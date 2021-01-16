//package com.company;

/* Created by Justin R. Moy for COP 3503 Prog Asn2*/

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

    public static void main(String[] args) throws IOException {

        File txtFile;
        File output;
        PrintWriter pen = null;

        int numVertices = 0;
        int source = 0;
        int numEdges;
        
        int[][] adjacencyMatrix = new int[0][];
        Integer[][] edges = new Integer[0][];
        vertex vertices[] = new vertex[0];

        FileInputStream file = null;
        int v1;
        int v2;
        int edgePrice;

        try{

            txtFile = new File("cop3503-asn2-input.txt");
            output = new File("cop3503-asn2-output-Moy-Justin.txt");
            pen = new PrintWriter(output);

            file = new FileInputStream(txtFile);
            Scanner in = new Scanner(file);

            numVertices = in.nextInt();
            source = in.nextInt()-1;
            numEdges = in.nextInt();
            vertices = new vertex[numVertices];

            for(int i = 0; i < numVertices; i++){
                vertices[i] = new vertex(null, Integer.MAX_VALUE, i+1);
            }

            vertices[source].changeParent(-1);
            vertices[source].changeDistance(0);

            adjacencyMatrix = new int[numVertices][numVertices];
            edges = new Integer[numVertices][numVertices];

            for(int r = 0; r < numVertices; r++){
                for(int c = 0; c < numVertices; c++){
                    adjacencyMatrix[r][c] = 0;
                    edges[r][c] = null;
                }
            }

            for(int i = 0; i < numEdges; i++){
                v1 = in.nextInt();
                v2 = in.nextInt();
                edgePrice = in.nextInt();

                adjacencyMatrix[v1-1][v2-1] = 1;
                adjacencyMatrix[v2-1][v1-1] = 1;
                edges[v1-1][v2-1] = edgePrice;
                edges[v2-1][v1-1] = edgePrice;
            }

            in.close();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            file.close();
        }

        int current = source;
        vertex minVertex = new vertex(null, Integer.MAX_VALUE, Integer.MAX_VALUE);

        while(!vertices[current].visited){
            vertices[current].visited = true;
            for(int c = 0; c < numVertices; c++){
                if (adjacencyMatrix[current][c] == 1) {

                    if ((vertices[current].distance + edges[current][c]) < vertices[c].distance) {
                        vertices[c].changeDistance(vertices[current].distance + edges[current][c]);
                        vertices[c].changeParent(current+1);
                    }
                }
                if(!vertices[c].visited) {
                    if (vertices[c].distance < minVertex.distance)
                        minVertex = vertices[c];
                    if (vertices[c].distance == minVertex.distance)
                        if(vertices[c].name < minVertex.name)
                            minVertex = vertices[c];
                }
            }
            current = minVertex.name-1;
            if(current >= numVertices) break;

            minVertex = new vertex(null, Integer.MAX_VALUE, Integer.MAX_VALUE);

        }

        //System.out.println(numVertices);
        pen.print(numVertices);
        vertices[source].changeDistance(-1);
        for(int r = 0; r < numVertices; r++){
            //System.out.println(vertices[r].name + " " + vertices[r].distance + " " + vertices[r].parent + " ");
            pen.print("\n" + vertices[r].name + " " + vertices[r].distance + " " + vertices[r].parent);
        }

        pen.flush();
        pen.close();
    }
}
