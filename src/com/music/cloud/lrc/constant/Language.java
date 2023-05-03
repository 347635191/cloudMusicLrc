package com.music.cloud.lrc.constant;

public enum Language {
    MAIN(0),
    SUB(1),
    DOUBLE(2);

    int type;

    Language(int type) {
        this.type = type;
    }

    public boolean isDouble() {
        return type == 2;
    }

    public boolean isSub(){
        return type == 1;
    }
}
