package model;

public class ModelError extends RuntimeException {
	
	public ModelError(String txtDeErro, Exception e) {
		super(txtDeErro);
		System.err.println(txtDeErro + "\n");
		printStackTrace();
	}
}
