package customer.barcode.barcodewebx.usermodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Userroot {

    @SerializedName("auth")
    @Expose
    private Auth auth;
    @SerializedName("roles")
    @Expose
    private Roles roles;

    public Auth getAuth() {
        return auth;
    }

    public void setAuth(Auth auth) {
        this.auth = auth;
    }

    public Roles getRoles() {
        return roles;
    }

    public void setRoles(Roles roles) {
        this.roles = roles;
    }

}