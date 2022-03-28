package com.namviet.vtvtravel.view.f3.deal.view.dealhome;

import com.namviet.vtvtravel.view.f3.deal.model.Block;

import java.util.ArrayList;
import java.util.List;

public final class ItemGenerator {

    private ItemGenerator() {

    }

    public static List<Item> largeListWithHeadersAt(int... positions) {
        List<Item> items = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            if (inArray(positions, i)) {
                items.add(new HeaderItem("Header at " + i, ""));
            } else {
                items.add(new Item("Item at " + i, "Item description at " + i));
            }
        }
        return items;
    }

    static List<Item> twoWithHeader() {
        List<Item> items = new ArrayList<>();
        items.add(new HeaderItem("header", ""));
        items.add(new Item("Item", ""));
        return items;
    }

    public static List<Item> demoList() {
        List<Item> items = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            if (i == 0 || i == 2) {
                items.add(new HeaderItem("Header at " + i, ""));
            } else {
                items.add(new Item("Item at " + i, "Item description at " + i));
            }

        }
        return items;
    }
    public static List<Item> demoTabDealList() {
        List<Item> items = new ArrayList<>();
        items.add(new Item("Tích lũy nhận quà", ""));
        items.add(new Item("Săn quà", ""));
        return items;
    }
    public static List<Item> demoTabDealList2() {
        List<Item> items = new ArrayList<>();
        items.add(new Item("Đang diễn ra", ""));
        items.add(new Item("Sắp diễn ra", ""));
        return items;
    }
    public static List<Item> demoTabHeader1() {
        List<Item> items = new ArrayList<>();
        items.add(new Item("Đi đâu", ""));
        items.add(new Item("Ở đâu", ""));
        items.add(new Item("Ăn gì", ""));
        items.add(new Item("Chơi gì", ""));
        items.add(new Item("Khác", ""));
        return items;
    }
    public static List<Item> demoListDeal() {
        List<Item> items = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
                items.add(new Item("Item at " + i, "Item description at " + i));
        }
        return items;
    }

    private static boolean inArray(int[] array, int value) {
        for (int i : array) {
            if (i == value) {
                return true;
            }
        }
        return false;
    }

    public static List<Block> demoTabDealList3() {
        List<Block> items = new ArrayList<>();
        items.add(new Block("Tích lũy nhận quà"));
        items.add(new Block("Săn quà"));
        return items;
    }
}
