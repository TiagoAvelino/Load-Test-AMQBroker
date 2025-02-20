package org.acme.amqp;

import java.util.Arrays;
import java.util.Random;

public class LargeMessage {
    private String id;
    private String name;
    private String description;
    private int[] numbers;
    private String largeText;

    public LargeMessage() {
        // Default constructor for deserialization
    }

    public LargeMessage(int sizeInBytes) {
        this.id = generateRandomString(10);
        this.name = generateRandomString(20);
        this.description = generateRandomString(50);
        this.numbers = generateLargeIntArray(10000); // Array to add data
        this.largeText = generateRandomString(sizeInBytes - estimateSize());
    }

    private String generateRandomString(int size) {
        char[] chars = new char[size];
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            chars[i] = (char) ('A' + random.nextInt(26)); // Random uppercase letters
        }
        return new String(chars);
    }

    private int[] generateLargeIntArray(int size) {
        int[] array = new int[size];
        Arrays.fill(array, 42); // Fill with some data
        return array;
    }

    private int estimateSize() {
        return id.length() + name.length() + description.length() + (numbers.length * Integer.BYTES);
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int[] getNumbers() {
        return numbers;
    }

    public String getLargeText() {
        return largeText;
    }
}
