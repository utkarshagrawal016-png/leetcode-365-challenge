class Solution {
    
    static boolean check(List<String> output, int row , int col){
        int n = output.size();
        int checkRow = row;
        int checkCol = 0;
        int Q_Count = 0;
        
        // Horizontally
        checkRow = row;
        checkCol = 0;
        Q_Count = 0;
        String workRow = output.get(checkRow);
        while (checkCol < n){
            char ch = workRow.charAt(checkCol);
            if (ch == 'Q') {
                Q_Count++;
                if (Q_Count >= 2) return false;
            }
            checkCol++;
        }

        // Vertically
        // checkRow = 0;
        // checkCol = col;
        // Q_Count = 0;
        // while (checkRow < n){
        //     char ch = output.get(checkRow).charAt(checkCol);
        //     if (ch == 'Q') {
        //         Q_Count++;
        //         if (Q_Count >= 2) return false;
        //     }
        //     checkRow++;
        // }

        //left diagonally 
        int mini = Math.min(row, col);
        checkRow = row-mini;
        checkCol = col-mini;
        Q_Count = 0;
        while ((checkRow >= 0 && checkRow < n) && (checkCol >= 0 && checkCol < n)){
            char ch = output.get(checkRow).charAt(checkCol);
            if (ch == 'Q') {
                Q_Count++;
                if (Q_Count >= 2) return false;
            }
            checkRow++;
            checkCol++;
        }

        //Right Diagonally

        int move = Math.min(row, n-1-col);
        checkRow = row-move;
        checkCol = col+move;
        Q_Count = 0;
        while ((checkRow >= 0 && checkRow < n) && (checkCol >= 0 && checkCol < n)){
            char ch = output.get(checkRow).charAt(checkCol);
            if (ch == 'Q'){
                Q_Count++;
                if (Q_Count >= 2) return false;
            }
            checkRow++;
            checkCol--;
        }
        return true;
    }
    
    static void solve(int n, int row, int col, List<String> output, List<List<String>> ans){        
        if (col >= n) {
            ans.add(new ArrayList<>(output));
            return;
        }
        if (row >= n){
            return;
        }

        String sub = output.get(row);
        String queenPlace = sub.substring(0, col) + "Q"  + sub.substring(col+1, n);
        output.set(row, queenPlace);
        if (check(output, row, col)){
            solve(n, 0, col+1, output, ans);
        }
        
        //undo/backtrack step
        output.set(row, sub);

        solve(n, row+1, col, output, ans);

    }

    static boolean safeToPlace(char[][] board, int rowIndex, int colIndex){
        //three direction
        // left horizontal
        // left upper diagnoal
        // left lower diagnol

        int n = board.length;
        int checkRow = 0;
        int checkCol = 0;

        // left horizontal
        checkRow = rowIndex;
        checkCol = colIndex;
        while (checkCol >= 0){
            if (board[checkRow][checkCol] == 'Q') return false;
            checkCol--;
        }

        // left upper diagnol
        checkRow = rowIndex;
        checkCol = colIndex;

        while ((checkRow >= 0) && (checkCol >= 0)){
            if (board[checkRow][checkCol] == 'Q') return false;
            checkRow--;
            checkCol--;
        }

        //left lower diagnol
        checkRow = rowIndex;
        checkCol = colIndex;
        while ((checkRow < n) && (checkCol >= 0)){
            if (board[checkRow][checkCol] == 'Q') return false;
            checkRow++;
            checkCol--;
        }
        return true;
    }

    static void solve2(int n, int colIndex, char[][] board, List<List<String>> ans){
        if (colIndex >= n){
            // Bhadai ho Arrangement ho gya
            List<String> temp = new ArrayList<>();
            for (int i = 0; i<n; i++){
                temp.add(new String(board[i]));
            }
            ans.add(temp);
            return;
        }

        for (int rowIndex = 0; rowIndex<n; rowIndex++){
            if (safeToPlace(board, rowIndex, colIndex)){
                board[rowIndex][colIndex] = 'Q';

                solve2(n, colIndex+1, board, ans);

                // undo/backtrack Step
                board[rowIndex][colIndex] = '.';
            }

        }

        
    }
    
    public List<List<String>> solveNQueens(int n) {
        // List<List<String>> ans = new ArrayList<>();
        // List<String> output = new ArrayList<>();
        // StringBuilder emptyRow= new StringBuilder(".".repeat(n));
        
        // for (int i = 0; i<n; i++){
        //     output.add(emptyRow.toString());
        // }
        // int row = 0;
        // int col = 0;
        // solve(n, row, col, output, ans);
        // return ans;
        List<List<String>> ans = new ArrayList<>();
        char[][] board = new char[n][n];

        for (int i = 0; i<n; i++){
            Arrays.fill(board[i], '.');

        }

        int colIndex = 0;
        solve2(n, colIndex, board, ans);
        return ans;
    }

}