package com.music.cloud.lrc.constant;

public enum FileName {
    MUSIC_ID(0),
    MUSIC_NAME(1);

    int type;

    FileName(int type) {
        this.type = type;
    }

    public boolean isMusicName() {
        return type == 1;
    }
}
