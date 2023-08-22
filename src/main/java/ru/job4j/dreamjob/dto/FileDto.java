package ru.job4j.dreamjob.dto;

public class FileDto {

    private String name;

    private byte[] content; /*tut kroetsja razlichie. domennaja model' hranit put', a ne soderzhimoe*/

    public FileDto(String name, byte[] content) {
        this.name = name;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

}
