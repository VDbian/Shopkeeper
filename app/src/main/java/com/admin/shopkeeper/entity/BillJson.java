package com.admin.shopkeeper.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/7/9 0009.
 * "Guid":"",
 * "Pice":"",
 * "PiceGuid":"",
 * "Sate":"",
 * "Type":"",
 * "IsSql":""
 * <p>
 * <p>
 * Pice":"23","PiceGuid":"5",
 * <p>
 * "Pice":"Type":""
 */

public class BillJson {

    public static class TeacherJson {
        private List<BillJsonBase> teacher;
        public List<BillJsonBase> getTeacher() {
            return teacher;
        }
        public void setTeacher(List<BillJsonBase> teacher) {
            this.teacher = teacher;
        }
    }

    public static class Quanxian {
        private List<BillJsonBase> quanxian;

        public List<BillJsonBase> getQuanxian() {
            return quanxian;
        }

        public void setQuanxian(List<BillJsonBase> quanxian) {
            this.quanxian = quanxian;
        }
    }

    public static class Pays {
        private List<BillJsonBase> pays;

        public List<BillJsonBase> getQuanxian() {
            return pays;
        }

        public void setQuanxian(List<BillJsonBase> pays) {
            this.pays = pays;
        }
    }

    public static class BillJsonBase {
        private String Guid = "";
        private String Pice = "";
        private String PiceGuid = "";
        private String Sate = "";
        private String Type = "";
        private String IsSql = "";


        public String getGuid() {
            return Guid;
        }

        public void setGuid(String guid) {
            Guid = guid;
        }

        public String getPice() {
            return Pice;
        }

        public void setPice(String pice) {
            Pice = pice;
        }

        public String getPiceGuid() {
            return PiceGuid;
        }

        public void setPiceGuid(String piceGuid) {
            PiceGuid = piceGuid;
        }

        public String getSate() {
            return Sate;
        }

        public void setSate(String sate) {
            Sate = sate;
        }

        public String getType() {
            return Type;
        }

        public void setType(String type) {
            Type = type;
        }

        public String getIsSql() {
            return IsSql;
        }

        public void setIsSql(String isSql) {
            IsSql = isSql;
        }
    }
}
