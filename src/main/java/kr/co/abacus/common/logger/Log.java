package kr.co.abacus.common.logger;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Log {
	public static void debug(String msg) {	
		log.debug("#### : " + msg);
	}
	
	public static void info(String msg) {
		log.info("#### : " + msg);
	}
}
