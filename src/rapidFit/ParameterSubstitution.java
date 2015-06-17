package rapidFit;

public class ParameterSubstitution {
	private String paramSubstitution;
	
	public ParameterSubstitution(){}
	
	public ParameterSubstitution(String paramSub){
		setParameterSubstitution(paramSub);
	}
	
	//accessor methods
	public void setParameterSubstitution(String paramSub){
		paramSubstitution = paramSub;
	}
	
	public String getParameterSubstitution(){
		return paramSubstitution;
	}
}
