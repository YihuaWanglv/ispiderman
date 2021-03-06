package com.iyihua.ispiderman.app;

import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.iyihua.ispiderman.core.HttpJsoupHelper;
import com.iyihua.ispiderman.entity.Repository;
import com.iyihua.ispiderman.service.MessageService;

//@Component
public class TaskGzsgsww {

	private static final Logger LOGGER = LoggerFactory.getLogger(TaskGzsgsww.class);

	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm:ss");
	public final static long ONE_MINUTE = 60 * 1000;
	public final static long ONE_DAY = 1000 * 60 * 60 * 24;

	@Value("${config.register.module.icms.gzsgsww}")
	private String CONFIG_REGISTER_MODULE_GZSGSWW;

	@Autowired
	private MessageService messageService;

	@Scheduled(fixedRate = ONE_DAY)
	public void execute() {
		LOGGER.info("TaskGzsgsww begin at {}", DATE_FORMAT.format(new Date()));
		String[] modules = CONFIG_REGISTER_MODULE_GZSGSWW.split(",");
		try {
			for (String module : modules) {
				List<Repository> list = HttpJsoupHelper.loadRepositorysGzsgsww(module);
				if (list != null && list.size() > 0) {
					for (Repository repository : list) {
						LOGGER.info(repository.toString());
						HttpJsoupHelper.loadRepositoryGzsgsww(repository);
						messageService.send("post-icms", repository);
					}
				}
			}
		} catch (SocketTimeoutException e) {
			LOGGER.error("spiderman execure catching failed: {}", e);
		}
	}
}
