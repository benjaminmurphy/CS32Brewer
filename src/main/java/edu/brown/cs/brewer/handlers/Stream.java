package edu.brown.cs.brewer.handlers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;

import edu.brown.cs.brewer.BrewerRuntime;

public class Stream {
  private int port;
  private int clientCount;

  public Stream(int port) {
    this.port = port;
    this.clientCount = 0;
    try {
      this.run();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void run() throws IOException {
    ServerSocket server = new ServerSocket(port);
    try {

      while (true) {
        new Runner(server.accept(), clientCount++).start();
      }
    } finally {
      server.close();
    }
  }

  public static class Runner extends Thread {
    private Socket client;
    private int num;
    private final Gson gson;
    private PrintWriter outgoing;

    public Runner(Socket client, int num) {
      this.client = client;
      this.num = num;
      this.gson = new Gson();
    }

    @Override
    public void run() {
      try {
        PrintWriter out = new PrintWriter(client.getOutputStream(), true);
        BufferedReader in =
            new BufferedReader(new InputStreamReader(client.getInputStream()));
        this.outgoing = out;

        while (!client.isClosed()) {
          String input = in.readLine();
          
          BrewerRuntime runtime = Parser.parseJSONProgram(input, this);
        }

      } catch (IOException e) {
        System.out.println("ERROR: Error while creating streams.");
      } finally {
        try {
          client.close();
        } catch (IOException e) {
          System.out.println("ERROR: Client connection couldn't be closed.");
        }
      }
    }

    public void message(String msg, Boolean isError) {
      ImmutableMap.Builder<String, Object> variables =
          new ImmutableMap.Builder<String, Object>();
      variables.put("msg", msg);
      if (isError) {
        variables.put("type", "error");
      } else {
        variables.put("type", "log");
      }

      outgoing.println(gson.toJson(variables.build()));
    }

    public void close() {
      try {
        client.close();
      } catch (IOException e) {
        System.out.println("ERROR: Client connection couldn't be closed.");
      }
    }

  }



}
