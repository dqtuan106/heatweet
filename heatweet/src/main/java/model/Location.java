package model;

public class Location {

	private int woeid;
	private double latitude;
	private double longitude;
	private String vizinhança;
	private String county;
	private String cidade;
	private String estado;

	public String getEndereco() {
		return vizinhança + " " + county + " " + cidade + " " + estado;
	}

	public int getWoeid() {
		return woeid;
	}

	public void setWoeid(int woeid) {
		this.woeid = woeid;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getVizinhança() {
		return vizinhança;
	}

	public void setVizinhança(String vizinhança) {
		this.vizinhança = vizinhança;
	}

}
