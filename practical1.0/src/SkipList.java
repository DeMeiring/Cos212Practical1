// Name:
// Student number:

import java.util.Random;

@SuppressWarnings("unchecked")
public class SkipList<T extends Comparable<? super T>>
{

	public int maxLevel;
	public SkipListNode<T>[] root;
	private int[] powers;
	private Random rd = new Random();

	SkipList(int i)
	{
		maxLevel = i;
		root = new SkipListNode[maxLevel];
		powers = new int[maxLevel];
		for (int j = 0; j < i; j++)
			root[j] = null;
		choosePowers();
		rd.setSeed(1230456789);
	}

	SkipList()
	{
		this(4);
	}

	public void choosePowers()
	{
		powers[maxLevel-1] = (2 << (maxLevel-1)) - 1;
		for (int i = maxLevel - 2, j = 0; i >= 0; i--, j++)
			powers[i] = powers[i+1] - (2 << j);
	}

	public int chooseLevel()
	{
		int i, r = Math.abs(rd.nextInt()) % powers[maxLevel-1] + 1;
		for (i = 1; i < maxLevel; i++)
		{
			if(r < powers[i])
				return i-1;
		}
		return i-1;
	}

	////// You may not change any code above this line //////

	////// Implement the functions below this line //////

	public boolean isEmpty()
	{
		//Your code goes here
	}

	public void insert(T key)
	{
		//Your code goes here
	}

	public boolean delete(T key)
	{
		//Your code goes here
	}

	public T first()
	{
		//Your code goes here
	}

	public T last()
	{
		//Your code goes here 
	}	

	public T search(T key)
	{
		//Your code goes here
	}
}