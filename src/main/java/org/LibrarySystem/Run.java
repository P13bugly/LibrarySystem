package org.LibrarySystem;

import org.LibrarySystem.Main_UI.MainInterface;

import java.awt.*;

public  class Run {
    public static void main(String[] args){
        EventQueue.invokeLater(() -> {    //跨线程通信(异步,线程安全)
            try {
                MainInterface frame = new MainInterface();
                frame.setVisible(true);
            } catch (RuntimeException e) {
                throw new RuntimeException(e);
            }
        });
    }
}