package de.chusek.sessionkeeper.model;

/**
 * Created by carsten on 13.02.2017.
 *
 * contains basic values for the DB
 * these are used in all models, so we make a base calss and inherit
 * noone should use this class on its own so it is abstract
 *
 * we could include the "toCV" & "fromCV" methods for conversion to and from ContentValues as abstract here
 * or make an interface CVable
 * but lets keep it simple
 */

public abstract class ABaseModel {

	//constant default values, can be used in All Models, so they are here, too
	protected final int DEFAULT_INT = -1;
	protected final String DEFAULT_STR = "nix";

	//values for all models
	protected int    id;
	protected String strCreatedDate;
	protected String strUpdatedDate;

	//region constructors
	public ABaseModel() {
		id = DEFAULT_INT;
		strCreatedDate = DEFAULT_STR;
		strUpdatedDate = DEFAULT_STR;
	}
	//endregion

	//region getter/setter


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCreatedDate() {
		return strCreatedDate;
	}

	public void setCreatedDate(String createdDate) {
		this.strCreatedDate = createdDate;
	}

	public String getStrUpdatedDate() {
		return strUpdatedDate;
	}

	public void setStrUpdatedDate(String strUpdatedDate) {
		this.strUpdatedDate = strUpdatedDate;
	}

	//endregion

	//region toSTRING for easier inheritance

	@Override
	public String toString() {
		//modified to fit better into super.toString of derived classes
		return 	"id=" + id +
				", createdDate='" + strCreatedDate + '\'' +
				", strUpdatedDate='" + strUpdatedDate + "' , ";
	}
	//endregion
}
