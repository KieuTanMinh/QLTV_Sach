package fpoly.duanmau.ph40749.Model;

public class LoaiSach {
    private int maLoai;
    private String tenLoai,cungcap;

    public LoaiSach() {
    }

    public LoaiSach(int maLoai, String tenLoai, String cungcap) {
        this.maLoai = maLoai;
        this.tenLoai = tenLoai;
        this.cungcap = cungcap;
    }

    public int getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(int maLoai) {
        this.maLoai = maLoai;
    }

    public String getTenLoai() {
        return tenLoai;
    }

    public void setTenLoai(String tenLoai) {
        this.tenLoai = tenLoai;
    }

    public String getCungcap() {
        return cungcap;
    }

    public void setCungcap(String cungcap) {
        this.cungcap = cungcap;
    }
}
