package sg.edu.nus.iss.paf_assessment.model;

import java.util.List;

public class FormFields {
    
    private String country;
    private int pax;
    private int min;
    private int max;
    private List<String> countries;

    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public int getPax() {
        return pax;
    }
    public void setPax(int pax) {
        this.pax = pax;
    }
    public int getMin() {
        return min;
    }
    public void setMin(int min) {
        this.min = min;
    }
    public int getMax() {
        return max;
    }
    public void setMax(int max) {
        this.max = max;
    }
    public List<String> getCountries() {
        return countries;
    }
    public void setCountries(List<String> countries) {
        this.countries = countries;
    }
    
}
