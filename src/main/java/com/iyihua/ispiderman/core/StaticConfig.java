package com.iyihua.ispiderman.core;

import java.util.Map;

import com.google.common.collect.Maps;

public class StaticConfig {

	
	public static Map<String, String> urls = Maps.newHashMap();

    static {
    	urls.put("gzaic_gskx", "http://www.gzaic.gov.cn/zwgk/gkml/gzdt/gskx/");
    	urls.put("gzaic_gsxw", "http://www.gzaic.gov.cn/zwgk/gkml/gzdt/gsxw/");
    	urls.put("gzsgsww_swyw", "http://www.gd-n-tax.gov.cn/pub/gzsgsww/ssxc/swyw/");
    	urls.put("gzsgsww_ssgg", "http://www.gd-n-tax.gov.cn/pub/001032/xxgk/tzgg/ssgg/");
    	urls.put("gzsgsww_gstg", "http://www.gd-n-tax.gov.cn/pub/001032/xxgk/tzgg/gstg/");
    	urls.put("gzsgsww_ssfg", "http://www.gd-n-tax.gov.cn/pub/gzsgsww/xxgk/ssfg/");
    }
    
    public interface Host {
		public static final String GZAIC = "http://www.gzaic.gov.cn";
	}
}
