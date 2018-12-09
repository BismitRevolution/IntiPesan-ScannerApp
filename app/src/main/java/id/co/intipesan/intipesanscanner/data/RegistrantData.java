package id.co.intipesan.intipesanscanner.data;

public class RegistrantData {

    private int registrant_id;
    private String registration_code;
    private String name;
    private String email;
    private String position;
    private String phone;
    private String company;
    private String company_address;
    private int payment_method;
    private int status;
    private int certificate;
    private String qr_path;
    private int event_id;
    private String created_at;
    private String updated_at;

    public String getRegistrationCode() {
        return this.registration_code;
    }

    public String getName() {
        return this.name;
    }

    public String getPosition() {
        return this.position;
    }

    public String getCompany() {
        return this.company;
    }
}
