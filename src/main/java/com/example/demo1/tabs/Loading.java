package com.example.demo1.tabs;

import javafx.scene.image.ImageView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Loading {

    public static void run() {
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    public static void createAndShowGUI() {
        JFrame frame = new JFrame("");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(200, 200);

        // Tạo một JLabel để chứa icon loading
        
        JLabel loadingLabel = new JLabel(new ImageIcon("load.gif"));
        loadingLabel.setHorizontalAlignment(JLabel.CENTER);

        // Thêm JLabel vào JFrame
        frame.getContentPane().add(loadingLabel, BorderLayout.CENTER);

        // Tạo một timer để đóng cửa sổ sau 2 giây
        Timer timer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        // Bắt đầu đếm thời gian
        timer.start();

        // Hiển thị cửa sổ
        frame.setLocationRelativeTo(null); // Đưa cửa sổ ra giữa màn hình
        frame.setVisible(true);
    }
}
