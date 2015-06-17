package rapidFit;

public class ConfigParam {
	
	private String configParam;
	
	public ConfigParam(){}
	
	public ConfigParam(String config){
		setConfigurationParameter(config);
	}
	
	//accessor methods
	public void setConfigurationParameter(String config){
		configParam = config;
	}
	
	public String getConfigurationParameter(){
		return configParam;
	}
}
