//package com.company;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {

    private static class vertex{ //vertex class will contain all the characteristics of a vertex for the graph
        Integer parent;
        int distance;
        int name;
        boolean visited;

        public vertex(Integer p, int d, int n){
            parent = p;
            distance = d;
            name = n;
            visited = false;
        }

        public void changeParent(int p){
            parent = p;
        }

        public void changeDistance(int d){
            distance = d;
        }
    }

    private static void bellmanFord(int numVertices, Integer[][] edges, vertex[] vertices, int[][] adjacencyMatrix, PrintWriter pen){

        for(int i = 0; i < numVertices-1; i++){
            for(int r = 0; r < numVertices; r++){
                for(int c = 0; c < numVertices; c++) {
                    if (adjacencyMatrix[r][c] == 1){
                        if(vertices[r].distance == Integer.MAX_VALUE) continue;
                        if((vertices[r].distance + edges[r][c]) < vertices[c].distance) {
                            vertices[c].changeDistance(vertices[r].distance + edges[r][c]);
                            vertices[c].changeParent(r+1);
                        }
                    }
                }
            }
        }

        pen.println(numVertices);
        for(int r = 0; r < numVertices; r++){
            pen.println(vertices[r].name + " " + vertices[r].distance + " " + vertices[r].parent);
        }

        pen.flush();
        pen.close();
    }

    private static void floydWarshall(int numVertices, Integer[][] pathCost, PrintWriter pen){

        int i = 0;
        int j = 0;

        for(int k = 0; k < numVertices; k++){
            for(i = 0; i < numVertices; i++){
                for(j = 0; j < numVertices; j++){
                    if(pathCost[i][k]  == Integer.MAX_VALUE || pathCost[k][j] == Integer.MAX_VALUE)
                        continue;
                    pathCost[i][j] = Integer.min(pathCost[i][j], pathCost[i][k] + pathCost[k][j]);
                }
            }
        }

        pen.println(numVertices);
        for(i = 0; i < numVertices; i++){
            for(j = 0; j < numVertices; j++){
                pen.print(pathCost[i][j] + " ");
            }
            pen.println();
        }

        pen.flush();
        pen.close();
    }

    public static void main(String[] args) throws IOException {

        File txtFile;
        File output;
        PrintWriter pen = null;

        int numVertices = 0;
        int source = 0;
        int numEdges;

        int[][] adjacencyMatrix = new int[0][];
        Integer[][] edges = new Integer[0][];
        Integer[][]  pathCost = new Integer[0][];
        vertex vertices[] = new vertex[0];

        FileInputStream file = null;
        int v1;
        int v2;
        int edgePrice;

        try{

            txtFile = new File("cop3503-asn3-input.txt");
            output = new File("cop3503-asn3-output-Moy-Justin-bf.txt");
            pen = new PrintWriter(output);

            file = new FileInputStream(txtFile);
            Scanner in = new Scanner(file);

            while(!in.hasNextInt())
                in.nextLine();
            numVertices = in.nextInt();
            while(!in.hasNextInt())
                in.nextLine();
            source = in.nextInt()-1;
            while(!in.hasNextInt())
                in.nextLine();
            numEdges = in.nextInt();
            while(!in.hasNextInt())
                in.nextLine();
            vertices = new vertex[numVertices];

            for(int i = 0; i < numVertices; i++){
                vertices[i] = new vertex(null, Integer.MAX_VALUE, i+1);
            }

            vertices[source].changeParent(0);
            vertices[source].changeDistance(0);

            adjacencyMatrix = new int[numVertices][numVertices];
            edges = new Integer[numVertices][numVertices];
            pathCost = new Integer[numVertices][numVertices];

            for(int r = 0; r < numVertices; r++){
                for(int c = 0; c < numVertices; c++){
                    adjacencyMatrix[r][c] = 0;
                    edges[r][c] = null;
                    if(r == c)
                        pathCost[r][c] = 0;
                    else
                        pathCost[r][c] = Integer.MAX_VALUE;
                }
            }

            for(int i = 0; i < numEdges; i++){
                while(!in.hasNextInt())
                    in.nextLine();
                v1 = in.nextInt();
                v2 = in.nextInt();
                edgePrice = in.nextInt();

                adjacencyMatrix[v1-1][v2-1] = 1;
                adjacencyMatrix[v2-1][v1-1] = 1;
                edges[v1-1][v2-1] = edgePrice;
                edges[v2-1][v1-1] = edgePrice;
                pathCost[v1-1][v2-1] = edgePrice;
                pathCost[v2-1][v1-1] = edgePrice;
            }

            in.close();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            file.close();
        }


        bellmanFord(numVertices, edges, vertices, adjacencyMatrix, pen);
        output = new File("cop3503-asn3-output-Moy-Justin-fw.txt");
        pen = new PrintWriter(output);
        floydWarshall(numVertices, pathCost, pen);

    }
}
