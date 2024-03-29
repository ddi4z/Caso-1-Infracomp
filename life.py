import random
# This code prints a random n*n matrix and the evolution of the Game of Life for a determined number of iterations

def randomNNmatrix(n):
    return [[random.choice([True,False]) for i in range(n)] for j in range(n)]


def printMatrix(matrix):
    with open("a.in", "w") as file:
        file.write(str(len(matrix)) + "\n")
        for row in matrix:
            file.write(",".join([str(i).lower() for i in row]) + "\n")

def printMatrix2(matrix):
    with open("a.out", "w") as file:
        iterations = 50
        for i in range(iterations):
            file.write("Turn " + str(i+1) + "\n")
            matrix = GameOfLife(matrix)

            n = len(matrix)

            for i in range(n):
                file.write("+")
                for j in range(n):
                    file.write("---+")
                file.write("\n")

                for j in range(n):
                    file.write("| ")
                    if matrix[i][j]:
                        file.write("* ")
                    else:
                        file.write("  ")
                file.write("|\n")

            file.write("+")
            for j in range(n):
                file.write("---+")
            file.write("\n")


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
    n = 50
    matrix = randomNNmatrix(n)
    # Comment the following line to test with a random matrix
    #matrix = sampleMatrix
    
    printMatrix(matrix)
    printMatrix2(matrix)


main()