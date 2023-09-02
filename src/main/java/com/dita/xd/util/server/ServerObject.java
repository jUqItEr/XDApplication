package com.dita.xd.util.server;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerObject {
    private Socket sock;
    private BufferedReader inputStream;
    private PrintWriter outputStream;

    public ServerObject(Socket sock, BufferedReader inputStream, PrintWriter outputStream) {
        this.sock = sock;
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    public Socket getSock() {
        return sock;
    }

    public void setSock(Socket sock) {
        this.sock = sock;
    }

    public BufferedReader getInputStream() {
        return inputStream;
    }

    public void setInputStream(BufferedReader inputStream) {
        this.inputStream = inputStream;
    }

    public PrintWriter getOutputStream() {
        return outputStream;
    }

    public void setOutputStream(PrintWriter outputStream) {
        this.outputStream = outputStream;
    }
}
