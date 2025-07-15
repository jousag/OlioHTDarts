public class GameStorage {
    private ArrayList<Game> games;
    private Game game;

    public GameStorage(Game game) {
        this.game = game;
        games = new ArrayList<>();
    }
    
    public void addGame(Game game) {
        games.add(game);
    }

    public Game getGame(int index) {
        if (index >= 0 && index < games.size()) {
            return games.get(index);
        }
        return null;
    }

    public void removeGame(Game game) {
        games.remove(game);
    }

    public ArrayList<Game> getAllGames() {
        return games;
    }
}
