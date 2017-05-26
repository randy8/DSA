/**
 * Processes commands and acts accordingly.
 * 
 * @author Randy Liang (randy8)
 * @author Patrick McKee (bludevil)
 * 
 * @version 2016.09.27
 */
public class CommandProcessor {

    private MemoryManager pool;
    private Hash artist;
    private Hash song;
    private Tree tree;

    /**
     * Creates a command processor.
     * 
     * @param hashSize
     *            size of hash table
     * @param blockSize
     *            size of free blocks
     */
    public CommandProcessor(int hashSize, int blockSize) {
        pool = new MemoryManager(blockSize);
        artist = new Hash("Artist", hashSize, pool);
        song = new Hash("Song", hashSize, pool);
        tree = new Tree();
    }

    /**
     * Insert string.
     * 
     * @param in
     *            string to be inserted
     */
    public void insert(String in) {
        String[] word = in.split("<SEP>");
        Handle hand1 = artist.search(word[0]);
        // for artists
        if (hand1 == null) {
            hand1 = pool.insert(word[0]);
            artist.insert(hand1);
            
            pool.expanded();
            
            System.out.println("|" + word[0] + "| "
                    + "is added to the artist database.");
        }
        else {
            System.out.println("|" + word[0] + "| "
                    + "duplicates a record already in the artist database.");
        }

        //songs
        Handle hand2 = song.search(word[1]);
        if (hand2 == null) {
            hand2 = pool.insert(word[1]);
            song.insert(hand2);
            
            pool.expanded();

            System.out.println("|" + word[1] + "| "
                    + "is added to the song database.");
        }
        else {
            System.out.println("|" + word[1] + "| "
                    + "duplicates a record already in the song database.");
        }

        //at this point hand1 and hand2 MUST have values
        KVPair pairArt = new KVPair(hand1, hand2);
        KVPair pairSong = new KVPair(hand2, hand1);

        // for KVPairs
        if (tree.insert(pairArt) != null) {
            System.out.println("The KVPair (|" + word[0] + "|,|"
                    + word[1] + "|)," + "(" + hand1.pos() + ","
                    + hand2.pos() + ")" + " is added to the tree.");
        }
        else {
            System.out.println("The KVPair (|" + word[0] + "|,|"
                    + word[1] + "|)," + "(" + hand1.pos() + ","
                    + hand2.pos() + ")"
                    + " duplicates a record already in the tree.");
        }
        if (tree.insert(pairSong) != null) {
            System.out.println("The KVPair (|" + word[1] + "|,|"
                    + word[0] + "|)," + "(" + hand2.pos() + ","
                    + hand1.pos() + ")" + " is added to the tree.");
        }
        else {
            System.out.println("The KVPair (|" + word[1] + "|,|"
                    + word[0] + "|)," + "(" + hand2.pos() + ","
                    + hand1.pos() + ")"
                    + " duplicates a record already in the tree.");
        }
    }

    /**
     * Removes the string.
     * 
     * @param in
     *            the string to be removed
     */
    public void remove(String in) {
        int index = in.indexOf(" ");
        String c2 = in.substring(0, index);
        String val = in.substring(index + 1);
        if (c2.equals("artist")) {
            Handle hand1 = artist.search(val);
            Handle[] songsOfArtist = tree.list(hand1);
            if (hand1 == null || songsOfArtist == null) {
                System.out.println("|" + val + "| "
                        + "does not exist in the artist database.");
                return;
            }
            else {
                for (int i = 0; i < songsOfArtist.length; i++) {
                    delete(val + "<SEP>"
                            + song.getString(songsOfArtist[i]), false,
                            true);              
                }
            }
        }
        else {
            Handle hand2 = song.search(val);
            Handle[] artistsOfSong = tree.list(hand2);
            if (hand2 == null || artistsOfSong == null) {
                System.out.println("|" + val + "| "
                        + "does not exist in the song database.");
                return;
            }
            else {
                for (int i = 0; i < artistsOfSong.length; i++) {
                    delete(artist.getString(artistsOfSong[i]) + "<SEP>"
                            + val, true, true);
                }                
            }
        }
    }

    /**
     * Prints the string of the respective handle.
     * 
     * @param in
     *            the string that is printed.
     */
    public void print(String in) {
        if (in.equals("artist")) {
            artist.print();
        }
        else if (in.equals("song")) {
            song.print();
        }
        else if (in.equals("tree")) {
            tree.print();
        }
        else {
            pool.dump();
        }
    }

    /**
     * Delete.
     * 
     * @param in
     *            Deleted value.
     * @param isSong
     *            whether or not the value to be deleted is a song or not
     * @param removeWork
     *              whether or not delete was called or this is a subfunction
     *              of remove
     */
    public void delete(String in, boolean isSong, boolean removeWork) {
        String[] word = in.split("<SEP>");
        Handle hand1 = artist.search(word[0]);
        Handle hand2 = song.search(word[1]);
        KVPair pairArt = new KVPair(hand1, hand2);
        KVPair pairSong = new KVPair(hand2, hand1);
        if (hand1 == null) {
            System.out.println("|" + word[0] + "| "
                    + "does not exist in the artist database.");
            return;
        }
        if (hand2 == null) {
            System.out.println("|" + word[1] + "| "
                    + "does not exist in the song database.");
            return;
        }
        if (!isSong) {
            if (tree.delete(pairArt)) {
                System.out.println("The KVPair (|" + word[0] + "|,|"
                        + word[1] + "|)" + " is deleted from the tree.");
            }
            if (tree.delete(pairSong)) {
                System.out.println("The KVPair (|" + word[1] + "|,|"
                        + word[0] + "|)" + " is deleted from the tree.");
            }
            if (!tree.contains(hand1)) {
                pool.remove(artist.remove(word[0]));

                System.out.println("|" + word[0]
                        + "| is deleted from the artist database.");
            }
            if (!tree.contains(hand2)) {
                pool.remove(song.remove(word[1]));

                System.out.println("|" + word[1]
                        + "| is deleted from the song database.");
            }
        }
        else {
            if (tree.delete(pairSong)) {
                System.out.println("The KVPair (|" + word[1] + "|,|"
                        + word[0] + "|)" + " is deleted from the tree.");
            }
            if (tree.delete(pairArt)) {
                System.out.println("The KVPair (|" + word[0] + "|,|"
                        + word[1] + "|)" + " is deleted from the tree.");
            }
            if (!tree.contains(hand2)) {
                pool.remove(song.remove(word[1]));

                System.out.println("|" + word[1]
                        + "| is deleted from the song database.");
            }
            if (!tree.contains(hand1)) {
                pool.remove(artist.remove(word[0]));

                System.out.println("|" + word[0]
                        + "| is deleted from the artist database.");
            }
        }
    }

    /**
     * List command processor
     * 
     * @param in
     *            input string
     */
    public void list(String in) {
        int index = in.indexOf(" ");
        String c2 = in.substring(0, index);
        String val = in.substring(index + 1);
        if (c2.equals("artist")) {
            Handle hand1 = artist.search(val);
            Handle[] handleList = tree.list(hand1);

            String artistList = null;
            if (handleList != null) {
                artistList = "";
                for (int i = 0; i < handleList.length; i++) {
                    artistList += "|" + artist.getString(handleList[i]) + "|";
                    if (i < handleList.length - 1) {
                        artistList += "\n";
                    }
                }
            }
            if (artistList == null) {
                System.out.println("|" + val + "| "
                        + "does not exist in the artist database.");
            }
            else {
                System.out.println(artistList);
            }
        }
        else {
            Handle handleS = song.search(val);
            Handle[] handleList = tree.list(handleS);

            String songList = null;
            if (handleList != null) {
                songList = "";
                for (int i = 0; i < handleList.length; i++) {
                    songList += "|" + song.getString(handleList[i]) + "|";
                    if (i < handleList.length - 1) {
                        songList += "\n";
                    }
                }
            }
            if (songList == null) {
                System.out.println("|" + val + "| "
                        + "does not exist in the song database.");
            }
            else {
                System.out.println(songList);
            }
        }
    }
}