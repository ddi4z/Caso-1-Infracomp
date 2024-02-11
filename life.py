import random

def randomNNmatrix(n):
    return [[random.choice([True,False]) for i in range(n)] for j in range(n)]


def printMatrix(matrix):
    for row in matrix:
        print(" ".join([str(i).lower() for i in row]))

def GameOfLife(matrix):
    n = len(matrix)
    newMatrix = [[False for i in range(n)] for j in range(n)]
    for i in range(n):
        for j in range(n):
            count = 0
            for a in range(-1,2):
                for b in range(-1,2):
                    if a == 0 and b == 0:
                        continue
                    if i+a < 0 or i+a >= n or j+b < 0 or j+b >= n:
                        continue
                    if matrix[i+a][j+b]:
                        count += 1
            if matrix[i][j]:
                if count == 0 or count > 3:
                    newMatrix[i][j] = False
                else:
                    newMatrix[i][j] = True
            else:
                if count == 3:
                    newMatrix[i][j] = True
    return newMatrix

sampleMatrix = [[False, True, False],
                [True, True, True],
                [False, False, False]]


def main():
    n = 3
    matrix = randomNNmatrix(n)
    # Comente la siguiente línea para probar con una matriz aleatoria
    #matrix = sampleMatrix
    print("Turno 0")
    printMatrix(matrix)

    iteraciones = 4
    for i in range(iteraciones):
        print("Turno", i+1)
        matrix = GameOfLife(matrix)
        printMatrix(matrix)

main()