package com.module3.model;

import com.module3.util.Console;

public interface Message {
        String choice = "Mời bạn lựa chọn: ";
        String continuous = "Bạn có muốn tiếp tục không (Y/N): ";
        default boolean confirm(){
                System.out.println(continuous);
                String confirm = Console.scanner.nextLine();
                return confirm.contains("y");
        }
}
