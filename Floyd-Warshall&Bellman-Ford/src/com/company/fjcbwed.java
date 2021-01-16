package com.company;

public class fjcbwed {





    for(int r = 0; r < numVertices; r++){
        for(int c = 0; c < numVertices; c++){
            if(r == newvertexIndex) {
                rowFlag = true;
                for (int i = 0; i < numVertices; i++)
                    newMatrix[r][i] =
                    if (c == newVertexIndex)
                        newMatrix[r][c] = origMatrix[r - 1][c - 1];
                    else
                        newMatrix[r][c] = origMatrix[r - 1][c];
                if (c == newVertexIndex)
                    newMatrix[r][c] = origMatrix[r][c - 1];
                else
                    newMatrix[r][c] = origMatrix[r][c];

        }
    }


}
