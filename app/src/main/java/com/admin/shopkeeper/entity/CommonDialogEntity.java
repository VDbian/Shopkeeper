package com.admin.shopkeeper.entity;

public class CommonDialogEntity {

    private String title;
    private String target;
    private boolean showTarget;
    private int peopleNum = 1;
    private boolean showPeople;
    private int canJuNum = 1;
    private boolean showCanJu;
    private boolean moreBtn;
    private String oneBtnText;
    private String leftBtnText;
    private String rightBtnText;
    private String curr;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public int getPeopleNum() {
        return peopleNum;
    }

    public void setPeopleNum(int peopleNum) {
        this.peopleNum = peopleNum;
    }

    public int getCanJuNum() {
        return canJuNum;
    }

    public void setCanJuNum(int canJuNum) {
        this.canJuNum = canJuNum;
    }

    public boolean isMoreBtn() {
        return moreBtn;
    }

    public void setMoreBtn(boolean moreBtn) {
        this.moreBtn = moreBtn;
    }

    public String getOneBtnText() {
        return oneBtnText;
    }

    public void setOneBtnText(String oneBtnText) {
        this.oneBtnText = oneBtnText;
    }

    public String getLeftBtnText() {
        return leftBtnText;
    }

    public void setLeftBtnText(String leftBtnText) {
        this.leftBtnText = leftBtnText;
    }

    public String getRightBtnText() {
        return rightBtnText;
    }

    public void setRightBtnText(String rightBtnText) {
        this.rightBtnText = rightBtnText;
    }

    public String getCurr() {
        return curr;
    }

    public void setCurr(String curr) {
        this.curr = curr;
    }

    public boolean isShowTarget() {
        return showTarget;
    }

    public void setShowTarget(boolean showTarget) {
        this.showTarget = showTarget;
    }

    public boolean isShowPeople() {
        return showPeople;
    }

    public void setShowPeople(boolean showPeople) {
        this.showPeople = showPeople;
    }

    public boolean isShowCanJu() {
        return showCanJu;
    }


    public void setShowCanJu(boolean showCanJu) {
        this.showCanJu = showCanJu;

    }
}