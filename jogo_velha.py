# Função para imprimir o tabuleiro
def print_board(board):
    for row in board:
        print(" | ".join(row))
        print("-" * 9)

# Função para verificar vitória
def check_winner(board, player):
    # Checa linhas, colunas e diagonais
    for row in board:
        if all([cell == player for cell in row]):
            return True
    for col in range(3):
        if all([board[row][col] == player for row in range(3)]):
            return True
    if all([board[i][i] == player for i in range(3)]) or all([board[i][2 - i] == player for i in range(3)]):
        return True
    return False

# Função para verificar se o tabuleiro está cheio
def is_board_full(board):
    return all([cell != " " for row in board for cell in row])

# Função principal do jogo
def tic_tac_toe():
    # Inicializa o tabuleiro vazio
    board = [[" " for _ in range(3)] for _ in range(3)]
    current_player = "X"

    # Loop do jogo
    while True:
        print_board(board)
        print(f"Vez do jogador {current_player}. Escolha sua posição (linha e coluna de 1 a 3):")
        
        # Recebe a entrada do jogador
        try:
            row = int(input("Linha: ")) - 1
            col = int(input("Coluna: ")) - 1
        except ValueError:
            print("Entrada inválida. Tente novamente.")
            continue

        # Verifica se a entrada é válida
        if row not in range(3) or col not in range(3) or board[row][col] != " ":
            print("Posição inválida. Tente novamente.")
            continue

        # Atualiza o tabuleiro
        board[row][col] = current_player

        # Verifica se o jogador atual ganhou
        if check_winner(board, current_player):
            print_board(board)
            print(f"Parabéns! O jogador {current_player} venceu!")
            break

        # Verifica se o tabuleiro está cheio
        if is_board_full(board):
            print_board(board)
            print("O jogo terminou em empate!")
            break

        # Alterna entre os jogadores
        current_player = "O" if current_player == "X" else "X"

# Inicia o jogo
tic_tac_toe()
