package customer.barcode.barcodewebx.usermodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Auth {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("first_name")
    @Expose
    private Object firstName;
    @SerializedName("last_name")
    @Expose
    private Object lastName;
    @SerializedName("middle_name")
    @Expose
    private Object middleName;
    @SerializedName("currency")
    @Expose
    private Object currency;
    @SerializedName("referral_code")
    @Expose
    private Object referralCode;
    @SerializedName("notify")
    @Expose
    private Integer notify;
    @SerializedName("login_with")
    @Expose
    private Object loginWith;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("image_id")
    @Expose
    private Object imageId;
    @SerializedName("image_external_path")
    @Expose
    private Object imageExternalPath;
    @SerializedName("company_id")
    @Expose
    private String companyId;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("mobile")
    @Expose
    private Object mobile;
    @SerializedName("address")
    @Expose
    private Object address;
    @SerializedName("city")
    @Expose
    private Object city;
    @SerializedName("country")
    @Expose
    private Object country;
    @SerializedName("enable_tracking")
    @Expose
    private Object enableTracking;
    @SerializedName("online")
    @Expose
    private Integer online;
    @SerializedName("store_id")
    @Expose
    private String storeId;
    @SerializedName("active")
    @Expose
    private Object active;
    @SerializedName("attendance")
    @Expose
    private String attendance;
    @SerializedName("vehicle_no")
    @Expose
    private Object vehicleNo;
    @SerializedName("vehicle_type")
    @Expose
    private Object vehicleType;
    @SerializedName("image")
    @Expose
    private Object image;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getFirstName() {
        return firstName;
    }

    public void setFirstName(Object firstName) {
        this.firstName = firstName;
    }

    public Object getLastName() {
        return lastName;
    }

    public void setLastName(Object lastName) {
        this.lastName = lastName;
    }

    public Object getMiddleName() {
        return middleName;
    }

    public void setMiddleName(Object middleName) {
        this.middleName = middleName;
    }

    public Object getCurrency() {
        return currency;
    }

    public void setCurrency(Object currency) {
        this.currency = currency;
    }

    public Object getReferralCode() {
        return referralCode;
    }

    public void setReferralCode(Object referralCode) {
        this.referralCode = referralCode;
    }

    public Integer getNotify() {
        return notify;
    }

    public void setNotify(Integer notify) {
        this.notify = notify;
    }

    public Object getLoginWith() {
        return loginWith;
    }

    public void setLoginWith(Object loginWith) {
        this.loginWith = loginWith;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Object getImageId() {
        return imageId;
    }

    public void setImageId(Object imageId) {
        this.imageId = imageId;
    }

    public Object getImageExternalPath() {
        return imageExternalPath;
    }

    public void setImageExternalPath(Object imageExternalPath) {
        this.imageExternalPath = imageExternalPath;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Object getMobile() {
        return mobile;
    }

    public void setMobile(Object mobile) {
        this.mobile = mobile;
    }

    public Object getAddress() {
        return address;
    }

    public void setAddress(Object address) {
        this.address = address;
    }

    public Object getCity() {
        return city;
    }

    public void setCity(Object city) {
        this.city = city;
    }

    public Object getCountry() {
        return country;
    }

    public void setCountry(Object country) {
        this.country = country;
    }

    public Object getEnableTracking() {
        return enableTracking;
    }

    public void setEnableTracking(Object enableTracking) {
        this.enableTracking = enableTracking;
    }

    public Integer getOnline() {
        return online;
    }

    public void setOnline(Integer online) {
        this.online = online;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public Object getActive() {
        return active;
    }

    public void setActive(Object active) {
        this.active = active;
    }

    public String getAttendance() {
        return attendance;
    }

    public void setAttendance(String attendance) {
        this.attendance = attendance;
    }

    public Object getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(Object vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public Object getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(Object vehicleType) {
        this.vehicleType = vehicleType;
    }

    public Object getImage() {
        return image;
    }

    public void setImage(Object image) {
        this.image = image;
    }

}