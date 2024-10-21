package election;

/* 
 * Election Analysis class which parses past election data for the house/senate
 * in csv format, and implements methods which can return information about candidates
 * and nationwide election results. 
 * 
 * It stores the election data by year, state, then election using nested linked structures.
 * 
 * The years field is a Singly linked list of YearNodes.
 * 
 * Each YearNode has a states Circularly linked list of StateNodes
 * 
 * Each StateNode has its own singly linked list of ElectionNodes, which are elections
 * that occured in that state, in that year.
 * 
 * This structure allows information about elections to be stored, by year and state.
 * 
 * @author Colin Sullivan
 */
public class ElectionAnalysis {

    // Reference to the front of the Years SLL
    private YearNode years;

    public YearNode years() {
        return years;
    }

    /*
     * Read through the lines in the given elections CSV file
     * 
     * Loop Though lines with StdIn.hasNextLine()
     * 
     * Split each line with:
     * String[] split = StdIn.readLine().split(",");
     * Then access the Year Name with split[4]
     * 
     * For each year you read, search the years Linked List
     * -If it is null, insert a new YearNode with the read year
     * -If you find the target year, skip (since it's already inserted)
     * 
     * If you don't find the read year:
     * -Insert a new YearNode at the end of the years list with the corresponding year.
     * 
     * @param file String filename to parse, in csv format.
     */
    public void readYears(String file) {
		// WRITE YOUR CODE HERE
      StdIn.setFile(file); // open file
      while(StdIn.hasNextLine()){ // read file line by line
        String[] split = StdIn.readLine().split(","); // split by commas
        int year = Integer.parseInt(split[4]); // get the year
        if(years == null){ // if ll is empty
          years = new YearNode(year); // insert the year
        }else{ //  if(years.getYear() != year)
          YearNode currentYear = years; // pointer
          while(currentYear.getNext() != null){   // traverse & keep going if year doesn't match // && currentYear.getYear() != year
            if(currentYear.getYear() == year){ // when year is found
              break; // comes out of loop when it finds the matching year
            }
            currentYear = currentYear.getNext(); // traverse
          }
          if(currentYear.getYear() != year ){ // if the year is not found //&& current.getNext() == null
            currentYear.setNext(new YearNode(year)); // insert new yearnode at end of ll
          }
        }
        
      }
    }

    /*
     * Read through the lines in the given elections CSV file
     * 
     * Loop Though lines with StdIn.hasNextLine()
     * 
     * Split each line with:
     * String[] split = StdIn.readLine().split(",");
     * Then access the State Name with split[1] and the year with split[4]
     * 
     * For each line you read, search the years Linked List for the given year.
     * 
     * In that year, search the states list. If the target state exists, continue
     * onto the next csv line. Else, insert a new state node at the END of that year's
     * states list (aka that years "states" reference will now point to that new node).
     * Remember the states list is circularly linked.
     * 
     * @param file String filename to parse, in csv format.
     */
    public void readStates(String file) { // cll
      // WRITE YOUR CODE HERE
      StdIn.setFile(file); // open file
      while (StdIn.hasNextLine()) { // read file line by line
        String[] split = StdIn.readLine().split(","); // split by commas
        int year = Integer.parseInt(split[4]); // year from the csv (have to match it to a year in the years ll)
        String state = split[1].trim(); // state from the csv (have to match it to a state in the cll)
        YearNode currentYear = years; // pointer for the years ll
        while (currentYear.getNext() != null && currentYear.getYear() != year) { // traverse & keep going if it doesn't match
          currentYear = currentYear.getNext(); // go to the next node
        } // comes out of loop when it finds the matching year
        if (currentYear != null) { // check not empty
          StateNode tailState = currentYear.getStates(); // get states list
          boolean foundState = false;
          if (tailState != null) { // check not empty
            StateNode currentState = tailState.getNext(); // insert state at end of cll
            do {
              if (currentState.getStateName().equalsIgnoreCase(state)) { // check match
                foundState = true; // found
                break;
              }
            } while (currentState != tailState.getNext()); // traverse until match
          }
          if (!foundState) { // if state not in list
            StateNode newState = new StateNode(state, null); // create new state
            if (tailState == null) { // if empty
              newState.setNext(newState); // new state points to itself
              currentYear.setStates(newState); // set it back
            } else { // if not empty
              newState.setNext(tailState.getNext()); // points to the state after tail
              tailState.setNext(newState); // tail points to new state
              currentYear.setStates(newState); // set it back
            }
          }
        }
      }
    }
    
    /*
     * Read in Elections from a given CSV file, and insert them in the
     * correct states list, inside the correct year node.
     * 
     * Each election has a unique ID, so multiple people (lines) can be inserted
     * into the same ElectionNode in a single year & state.
     * 
     * Before we insert the candidate, we should check that they dont exist already.
     * If they do exist, instead modify their information new data.
     * 
     * The ElectionNode class contains addCandidate() and modifyCandidate() methods for you to use.
     * 
     * @param file String filename of CSV to read from
     */
      @SuppressWarnings("empty-statement")
    public void readElections(String file){
		  // WRITE YOUR CODE HERE
      StdIn.setFile(file); // set file
      while(StdIn.hasNextLine()){
        String line = StdIn.readLine(); // read line
        String[] split = line.split(","); // split by commas
        int raceID = Integer.parseInt(split[0]); // getting info
        String stateName = split[1];
        int officeID = Integer.parseInt(split[2]);
        boolean senate = split[3].equals("U.S. Senate");
        int year = Integer.parseInt(split[4]);
        String canName = split[5];
        String party = split[6];
        int votes = Integer.parseInt(split[7]);
        boolean winner = split[8].toLowerCase().equals("true");

        // find the year
        YearNode currentYear = years; // pointer for years list
        while(currentYear.getNext() != null && currentYear.getYear() != year){ // traverses through the years ll
          currentYear = currentYear.getNext();
        } // breaks when year is found

        // find the state
        if(currentYear != null) { // check empty (unnecessary but just to be safe)
          StateNode currentState = currentYear.getStates(); // pointer for traversal
          StateNode headState = currentState; // pointer to head of states list
          do {
            if (currentState.getStateName().equals(stateName)) { // check match
              break;
            } // breaks from loop when found
            currentState = currentState.getNext();
          } while (currentState != headState); // go through the list
          if(currentState != null){ // check empty
            if(currentState.getElections() == null){ // if empty
              ElectionNode newElection = new ElectionNode(); // create new election node
              newElection.setRaceID(raceID); // set raceID
              newElection.setSenate(senate); // set senate
              newElection.setoOfficeID(officeID); // set officeID
              newElection.addCandidate(canName, votes, party, winner); // add candidate
              currentState.setElections(newElection); // insert new election
            }else{
              ElectionNode currentElection = currentState.getElections();  // pointer for traversal
              ElectionNode tailElection = null; // pointer to tail
              while(currentElection != null && currentElection.getRaceID() != raceID){ 
                tailElection = currentElection;
                currentElection = currentElection.getNext();
              } // breaks when empty or when found
              if(currentElection != null){ // if found
                if(currentElection.isCandidate(canName)){ // if candidate is there
                  currentElection.modifyCandidate(canName,votes,party); // modify
                }else{ // if candidate is not there
                  currentElection.addCandidate(canName,votes,party,winner); // add candidate
                }
              }else{ // if not found
                ElectionNode newElection = new ElectionNode(); // create new election node
                newElection.setRaceID(raceID); // set raceID
                newElection.setSenate(senate); // set senate
                newElection.setoOfficeID(officeID); // set officeID
                newElection.addCandidate(canName, votes, party, winner); // add candidate
                if(tailElection != null){ 
                  tailElection.setNext(newElection); // insert new node
                }


              }
            }
          }
        }
      }
    }

    // /*
    //  * DO NOT EDIT
    //  * 
    //  * Calls the next method to get the difference in voter turnout between two
    //  * years
    //  * 
    //  * @param int firstYear First year to track
    //  * 
    //  * @param int secondYear Second year to track
    //  * 
    //  * @param String state State name to track elections in
    //  * 
    // //  * @return int Change in voter turnout between two years in that state
    //  */
    // public int changeInTurnout(int firstYear, int secondYear, String state) {
    //     // DO NOT EDIT
    //     int last = totalVotes(firstYear, state);
    //     int first = totalVotes(secondYear, state);
    //     return last - first;
    // }

    // /*
    //  * Given a state name, find the total number of votes cast
    //  * in all elections in that state in the given year and return that number
    //  * 
    //  * If no elections occured in that state in that year, return 0
    //  * 
    //  * Use the ElectionNode method getVotes() to get the total votes for any single
    //  * election
    //  * 
    //  * @param year The year to track votes in
    //  * 
    //  * @param stateName The state to track votes for
    //  * 
    //  * @return avg number of votes this state in this year
    //  */
    public int totalVotes(int year, String stateName) {
      	// WRITE YOUR CODE HERE
      // find year
      YearNode currentYear = years; // pointer for years
      while(currentYear != null){ // if not empty
        if(currentYear.getYear() == year){ // if match
          break;
        } // breaks when found
        currentYear = currentYear.getNext(); // traverse
      }
      if(currentYear == null){ // if empty
        return 0;
      }

      // find state
      StateNode currentState = currentYear.getStates(); // pointer for states     
      do{
        if(currentState.getStateName().equalsIgnoreCase(stateName)){ // if match
          break;
        } // breaks when found
        currentState = currentState.getNext(); // traverse
      }while(currentState != currentYear.getStates()); // until end

      if(currentState == null){ // if empty
        return 0;
      }

      // count votes
      int countVotes = 0; // counter var
      ElectionNode currentElection = currentState.getElections(); // pointer for elections
      while(currentElection != null){  // check empty
        countVotes += currentElection.getVotes(); // add votes
        currentElection = currentElection.getNext(); // continue
      }
      return countVotes;
    }

      /*
     * DO NOT EDIT
     * 
     * Calls the next method to get the difference in voter turnout between two
     * years
     * 
     * @param int firstYear First year to track
     * 
     * @param int secondYear Second year to track
     * 
     * @param String state State name to track elections in
     * 
    //  * @return int Change in voter turnout between two years in that state
     */

      /*
     * DO NOT EDIT
     * 
     * Calls the next method to get the difference in voter turnout between two
     * years
     * 
     * @param int firstYear First year to track
     * 
     * @param int secondYear Second year to track
     * 
     * @param String state State name to track elections in
     * 
    //  * @return int Change in voter turnout between two years in that state
     */

    // *****************************************************************************************************************************************************
    // ********************** I MOVED THIS METHOD BECAUSE IT WAS GIVING ME ERRORS IN THE METHOD HEADER AND IN THE totalVotes CALL***************************
    // *****************************************************************************************************************************************************
    public int changeInTurnout(int firstYear, int secondYear, String state) {
      // DO NOT EDIT
      int last = totalVotes(firstYear, state);
      int first = totalVotes(secondYear, state);
      return last - first;
  }
    /*
     * Given a state name and a year, find the average number of votes in that
     * state's elections in the given year
     * 
     * @param year The year to track votes in
     * 
     * @param stateName The state to track votes for
     * 
     * @return avg number of votes this state in this year
     */
    public int averageVotes(int year, String stateName) {
      	// WRITE YOUR CODE HERE
      // find year
      YearNode currentYear = years; // pointer for years
      while(currentYear != null){ // check empty
        if(currentYear.getYear() == year){ // check match
          break; // break when found
        }
        currentYear = currentYear.getNext(); // traverse
      }
      if(currentYear == null){ // if empty
        return 0;
      }

      // find state
      StateNode currentState = currentYear.getStates(); // pointer for states
      do{
        if(currentState.getStateName().equalsIgnoreCase(stateName)){ // check match
          break;
        } // breaks when found
        currentState = currentState.getNext(); // traverse
      } while(currentState != currentYear.getStates()); // until end
      if(currentState == null){ // if not found
        return 0;
      }

      // count
      int countVotes = 0; // vote counter (numerator)
      int countElections = 0; // election counter (denominator)
      ElectionNode currentElection = currentState.getElections(); // pointer for elections
      while(currentElection != null){ // check empty
        countVotes += currentElection.getVotes(); // add votes
        countElections ++; // add 1 election
        currentElection = currentElection.getNext(); // continue
      }
      int avg = countVotes/countElections; // calculate average (int division because this method returns int)
      return avg;
    }

    /*
     * Given a candidate name, return the party they FIRST ran with
     * 
     * Search each year node for elections with the given candidate
     * name. Update that party each time you see the candidates name and
     * return the party they FIRST ran with
     * 
     * @param candidateName name to find
     * 
     * @return String party abbreviation
     */
    public String candidatesParty(String candidateName) {
		  // WRITE YOUR CODE HERE
      String party = ""; // party for returning
      YearNode currentYear = years; // pointer for years
      while(currentYear != null){ // check empty
        StateNode currentState = currentYear.getStates(); // pointer for states
        if(currentState != null){ // check empty
          do{
            ElectionNode currentElection = currentState.getElections(); // pointer for elections
            while(currentElection != null){ // check empty
              if(currentElection.isCandidate(candidateName)){ // check if candidate is there
                party = currentElection.getParty(candidateName); // get candidate's party
              }
              currentElection = currentElection.getNext(); // continue
            }
            currentState = currentState.getNext(); // continue
          } while(currentState != currentYear.getStates()); // until end
      }
        currentYear = currentYear.getNext(); // continue
      }
      return party;
    }
}