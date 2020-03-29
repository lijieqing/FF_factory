package com.fengmi.factory_test_interf.sdk_data;


import com.fengmi.factory_test_interf.SDKManager;

/**
 * DLP 光机命令
 *
 * @author lijie
 * @create 2018-08-12 20:53
 **/
public abstract class DLPCmd {
    protected static final String TAG = "Factory_DLPCmd";

    private static final String TYPE_EVA = "eva";
    private static final String TYPE_ANGLEE = "angleeUHD";
    private static final String TYPE_FRANKY = "franky";
    private static final String TYPE_GOBLIN = "goblin";
    private static final String TYPE_IRONMAN = "ironman";

    /**
     * get string [] of DLP screen check cmd
     *
     * @return String[]
     */
    public abstract String[] getImageModes();

    /**
     * get DLP Screen Check Start or Close Cmd
     *
     * @param open boolean
     * @return String
     */
    public abstract String getScreenCheckInitCmd(boolean open);

    /**
     * get XPR check start or close Cmd
     *
     * @param open boolean
     * @return String
     */
    public abstract String getXPRCheckInitCmd(boolean open);

    /**
     * get XPR shake on or off Cmd
     *
     * @param on boolean
     * @return String
     */
    public abstract String getXPRShakeCmd(boolean on);

    /**
     * get image command path
     *
     * @return string
     */
    public abstract String getImageCMDPath();

    /**
     * get DLP Init Command Path
     *
     * @return string
     */
    public abstract String getImageInitCMDPath();

    /**
     * init DLPCmd by Build.DEVICE
     *
     * @param type Build.DEVICE
     * @return instance of DLPCmd
     */
    public static DLPCmd initDLPCmd(String type) {
        DLPCmd cmd;
        switch (type) {
            case TYPE_ANGLEE:
            case TYPE_FRANKY:
            case TYPE_GOBLIN:
                cmd = new Anglee_P_DLPCMD();
                break;
            case TYPE_IRONMAN:
                String hwID = SDKManager.getAndroidOSManagerInterf().getSystemProperty("ro.boot.hardware_id", "");
                if (hwID.equals("2")) {
                    cmd = new IronMan_DLPCmd_045();
                } else {
                    cmd = new EVA_P_DLPCMD();
                }
                break;
            default://EVA and default
                cmd = new EVA_P_DLPCMD();
                break;
        }
        return cmd;
    }


}


class Anglee_P_DLPCMD extends DLPCmd {
    private static final String CMD_COLOR_INIT = "3 f0 04 00";
    private static final String CMD_COLOR_EXIT = "3 f0 01 00";

    private static final String CMD_XPR_INIT = "3 f0 07 01";
    private static final String CMD_XPR_EXIT = "3 f0 07 00";

    private static final String CMD_XPR_SHAKE_ON = "7 DA 00 00 01 00 00 00";
    private static final String CMD_XPR_SHAKE_OFF = "7 DA 00 00 00 00 00 00";

    private static final String CMD_COLOR_RED = "3 f0 23 00";
    private static final String CMD_COLOR_GREEN = "3 f0 23 01";
    private static final String CMD_COLOR_BLUE = "3 f0 23 02";
    private static final String CMD_COLOR_BLACK = "3 f0 23 03";
    private static final String CMD_COLOR_WHITE = "3 f0 23 04";
    private static final String CMD_COLOR_GRAY_HORIZONTAL = "3 f0 23 05";
    private static final String CMD_COLOR_GRAY_VERTICAL = "3 f0 23 06";
    // 棋盘
    private static final String CMD_GRADATION_GRID = "3 f0 23 07";
    // 网格
    private static final String CMD_GRADATION_RESEAU = "3 f0 23 08";
    private static final String CMD_GRADATION_DIAMOND = "3 f0 23 09";
    private static final String CMD_GRADATION_LINE_HORIZONTAL = "3 f0 23 0a";
    private static final String CMD_GRADATION_LINE_VERTICAL = "3 f0 23 0b";


    @Override
    public String[] getImageModes() {
        return new String[]{
                CMD_COLOR_RED,CMD_COLOR_GREEN,CMD_COLOR_BLUE,
                CMD_COLOR_WHITE, CMD_COLOR_BLACK,
                CMD_COLOR_GRAY_HORIZONTAL, CMD_COLOR_GRAY_VERTICAL,
                CMD_GRADATION_DIAMOND, CMD_GRADATION_LINE_HORIZONTAL,
                CMD_GRADATION_LINE_VERTICAL, CMD_GRADATION_GRID,
                CMD_GRADATION_RESEAU
        };
    }

    @Override
    public String getScreenCheckInitCmd(boolean open) {
        if (open) {
            return CMD_COLOR_INIT;
        }
        return CMD_COLOR_EXIT;
    }

    @Override
    public String getXPRCheckInitCmd(boolean open) {
        if (open){
            return CMD_XPR_INIT;
        }
        return CMD_XPR_EXIT;
    }

    @Override
    public String getXPRShakeCmd(boolean on) {
        if (on){
            return CMD_XPR_SHAKE_ON;
        }
        return CMD_XPR_SHAKE_OFF;
    }

    @Override
    public String getImageCMDPath() {
        return "/sys/class/projector/dlp/write_reg";
    }

    @Override
    public String getImageInitCMDPath() {
        return "/sys/class/projector/dlp/write_reg";
    }
}

class EVA_P_DLPCMD extends DLPCmd {
    private static final String CMD_COLOR_INIT_CONAN = "2 62 00";
    private static final String CMD_COLOR_EXIT_CONAN = "2 62 02";

    private static final String CMD_COLOR_WHITE_CONAN = "3 67 f0 00";
    private static final String CMD_COLOR_GREEN_CONAN = "3 67 c0 00";
    private static final String CMD_COLOR_RED_CONAN = "3 67 a0 00";
    private static final String CMD_COLOR_BLUE_CONAN = "3 67 90 00";
    private static final String CMD_COLOR_BLACK_CONAN = "3 67 00 00";
    private static final String CMD_COLOR_GRAY_HORIZONTAL = "3 67 02 ff";
    private static final String CMD_COLOR_GRAY_VERTICAL = "3 67 03 ff";
    private static final String CMD_GRADATION_RESEAU = "3 67 81 00";
    private static final String CMD_XPR_PIC_CONAN = "3 67 08 00";

    @Override
    public String[] getImageModes() {
        return new String[]{
                CMD_COLOR_WHITE_CONAN,
                CMD_COLOR_GREEN_CONAN, CMD_COLOR_RED_CONAN, CMD_COLOR_BLUE_CONAN,
                CMD_GRADATION_RESEAU, CMD_XPR_PIC_CONAN, CMD_COLOR_BLACK_CONAN,
                CMD_COLOR_GRAY_HORIZONTAL, CMD_COLOR_GRAY_VERTICAL
        };
    }

    @Override
    public String getScreenCheckInitCmd(boolean open) {
        if (open) {
            return CMD_COLOR_INIT_CONAN;
        }
        return CMD_COLOR_EXIT_CONAN;
    }

    @Override
    public String getXPRCheckInitCmd(boolean open) {
        return "";
    }

    @Override
    public String getXPRShakeCmd(boolean on) {
        return "";
    }

    @Override
    public String getImageCMDPath() {
        return "/sys/class/projector/dlp/write_reg";
    }

    @Override
    public String getImageInitCMDPath() {
        return "/sys/class/projector/dlp/write_reg";
    }
}


class IronMan_DLPCmd_045 extends DLPCmd {
    private static final String DLP_PATTERN_INIT = "3 05 00 00";
    private static final String DLP_PATTERN_EXIT = "3 05 02 00";

    private static final String CMD_COLOR_BAR = "3 67 0a 00";
    private static final String CMD_COLOR_BLACK = "3 67 00 00";
    private static final String CMD_COLOR_RED = "3 67 20 00";
    private static final String CMD_COLOR_GREEN = "3 67 40 00";
    private static final String CMD_COLOR_BLUE = "3 67 19 99";

    @Override
    public String[] getImageModes() {
        return new String[]{
                CMD_COLOR_BAR, CMD_COLOR_RED, CMD_COLOR_GREEN,
                CMD_COLOR_BLUE, CMD_COLOR_BLACK};
    }

    @Override
    public String getScreenCheckInitCmd(boolean open) {
        if (open) {
            return DLP_PATTERN_INIT;
        }
        return DLP_PATTERN_EXIT;
    }

    @Override
    public String getXPRCheckInitCmd(boolean open) {
        return null;
    }

    @Override
    public String getXPRShakeCmd(boolean on) {
        return null;
    }

    @Override
    public String getImageCMDPath() {
        return "/sys/class/projector/dlp/write_reg";
    }

    @Override
    public String getImageInitCMDPath() {
        return "/sys/class/projector/dlp/write_reg";
    }
}
