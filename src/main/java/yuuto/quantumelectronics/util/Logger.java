package yuuto.quantumelectronics.util;

import org.apache.logging.log4j.Level;

import yuuto.quantumelectronics.ref.References;
import cpw.mods.fml.common.FMLLog;



public class Logger {
	private static final void LogMessage(Level level, Object o)
	{
		FMLLog.log(References.MOD_ID, level, String.valueOf(o), o);
	}
	
	public static final void Log(Object o){LogMessage(Level.ALL, o);}
	
	public static final void LogDebug(Object o){LogMessage(Level.DEBUG, o);}
	
	public static final void LogError(Object o){LogMessage(Level.ERROR, o);}
	
	public static final void LogFatal(Object o){LogMessage(Level.FATAL, o);}
	
	public static final void LogInfo(Object o){LogMessage(Level.INFO, o);}
	
	public static final void LogOff(Object o){LogMessage(Level.OFF, o);}
	
	public static final void LogTrace(Object o){LogMessage(Level.TRACE, o);}
	
	public static final void LogWarning(Object o){LogMessage(Level.WARN, o);}
}
