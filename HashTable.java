public class HashTable {
  // a field to store the hash entries into the table
  private HashEntry[] list;
  // a field to store the number of items added in the table
  private int numItems;
  // a field to store the capacity of the table
  private int capacity;

  public HashTable() {
    // assigns the list array to a new hash entry array with capacity 30
    list = new HashEntry[300];
    capacity = 300;
  }

  public HashTable(int n) {
    // assigns the list array to a new hash entry array with capacity n
    list = new HashEntry[n];
    capacity = n;
  }

  public void put(String key, int value) {
    // gets the index of the key and mod it to the capacity
    int index = key.hashCode() % capacity;
    // gets the absolute value of the index
    if (index < 0) {
      index = index * -1;
    }
    // if the index position is empty, the position is free to be inserted a new
    // element
    if (list[index] == null) {
      // checks if there is a need to rehash the table
      if (loadFactor() > 0.7) {
        HashTable tab = rehash();
        list = tab.list;
        capacity = tab.capacity;
      }
      // inserts a new entry with key and value on the index position
      list[index] = new HashEntry(key, value);
      numItems++;
    }
    // the index position is not empty in the table
    else {
      // loops to find an empty position to insert the element (linear probing)
      while (list[index] != null) {
        // in case the key already exists, only the value is updated
        if (list[index].getKey().equals(key)) {
          list[index].setValue(value);
          // no need to keep on looping, so returns
          return;
        }
        index++;
        index = index % capacity;
      }
      // checks if there is a need to rehash the table
      if (loadFactor() > 0.7) {
        HashTable tab = rehash();
        list = tab.list;
        capacity = tab.capacity;
      }
      // found an empty spot to insert a new hash entry
      list[index] = new HashEntry(key, value);
      numItems++;

    }

  }

  public void put(String key, int value, int hashCode) {
    // mods the hashCode
    int index = hashCode % capacity;
    // gets the absolute value of the index
    if (index < 0) {
      index = index * -1;
    }
    // if the index position is empty, the position is free to be inserted a new
    // element
    if (list[index] == null) {
      // checks if there is a need to rehash the table
      if (loadFactor() > 0.7) {
        HashTable tab = rehash();
        list = tab.list;
        capacity = tab.capacity;
      }
      // inserts a new entry with key and value on the index position
      list[index] = new HashEntry(key, value);
      numItems++;
    }
    // the index position is not empty in the table
    else {
      // loops to find an empty position to insert the element (linear probing)
      while (list[index] != null) {
        // in case the key already exists, only the value is updated
        if (list[index].getKey().equals(key)) {
          list[index].setValue(value);
          // no need to keep looping, so returns
          return;
        }
        index++;
        index = index % capacity;
      }
      // checks if the table needs to be rehashed
      if (loadFactor() > 0.7) {
        HashTable tab = rehash();
        list = tab.list;
        capacity = tab.capacity;
      }
      // found an empty spot to insert the new element
      list[index] = new HashEntry(key, value);
      numItems++;
    }

  }

  public void update(String key, int value) {
    // the put method updates values accordingly
    put(key, value);
  }

  public int get(String key) {
    // gets the hashCode of the key and mods it to the capacity
    int index = key.hashCode() % capacity;
    // gets the absolute value of the index
    if (index < 0) {
      index = index * -1;
    }
    if (list[index] != null)
      // if the element at that index is equal to the key, return the value
      if (list[index].getKey().equals(key)) {
        return list[index].getValue();
      }
      // the element is not the same as the key
      else {
        // loops to see if the element exists elsewhere
        while (list[index] != null) {
          index++;
          index = index % capacity;
          if (list[index] != null)
            // found the element, returns it's value
            if (list[index].getKey().equals(key)) {
              return list[index].getValue();
            }
        }
      }

    return -1;
  }

  public int get(String key, int hashCode) {
    // created an index equal to the hashCode and mods it to the capacity
    int index = hashCode % capacity;
    // gets the absolute value of the index
    if (index < 0) {
      index = index * -1;
    }
    if (list[index] != null)
      // found the element and returns the value
      if (list[index].getKey().equals(key)) {
        return list[index].getValue();
      }
    // didn't find it, loops down to check if it exists elsewhere
    while (list[index] != null) {
      index++;
      index = index % capacity;
      if (list[index] != null)
        // found the element, returns it's value
        if (list[index].getKey().equals(key)) {
          return list[index].getValue();
        }
    }
    return -1;
  }

  protected double loadFactor() {
    return (double) size() / (double) capacity();
  }

  protected HashTable rehash() {
    // creates a new HashTable with twice the capacity of this HashTable
    HashTable tab = new HashTable(capacity * 2);
    // loops around and puts elements from this table to the new table
    for (int i = 0; i < capacity; i++) {
      if (list[i] != null) {
        for (int j = 0; j < list[i].getValue(); j++) {
          tab.put(list[i].getKey(), list[i].getValue());
        }
      }
    }
    return tab;
  }

  /*
   * .................................helper and getter/setter
   * methods................................
   */
  public int capacity() {
    return capacity;
  }

  public void setCapacity(int i) {
    capacity = i;
  }

  public int size() {
    return numItems;
  }

  public void setSize(int i) {
    numItems = i;
  }

  public HashEntry[] entry() {
    return list;
  }

  public void setEntry(HashEntry[] a) {
    list = a;
  }

  public void print() {
    for (int i = 0; i < capacity; i++) {
      if (list[i] != null)
        System.out.println(list[i].getKey() + "  " + list[i].getValue());
      else {
        System.out.println("........");
      }

    }
  }

}
