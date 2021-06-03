package com.epam.fifthtask.entity;

import com.epam.fifthtask.entity.type.ContainerContent;

import java.util.Random;

public class WagonContainer {
    private final ContainerContent content;

    public WagonContainer() {
        Random ran = new Random();
        int a = ran.nextInt(10);
        this.content = a > 7 ? ContainerContent.PERISHABLE_PRODUCTS : ContainerContent.NORMAL_PRODUCTS;

    }

    public ContainerContent getContent() {
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        WagonContainer that = (WagonContainer) o;

        return getContent() == that.getContent();
    }

    @Override
    public int hashCode() {
        return getContent() != null ? getContent().hashCode() : 0;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("content: ");
        sb.append(content);
        return sb.toString().toLowerCase();
    }
}
