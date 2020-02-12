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
		return root[0].next == null;
	}

	public void insert(T key)
	{
		SkipListNode[] curr = new SkipListNode[maxLevel];
		SkipListNode[] prev = new SkipListNode[maxLevel];
		SkipListNode newNode;
		int lvl,i;

		curr[maxLevel-1] = root[maxLevel-1];
		prev[maxLevel-1]=null;

		for(lvl=maxLevel-1;lvl>=0;lvl--){	//loop to test each inside condition of each level
			while(curr[lvl]!=null && key.compareTo((T)curr[lvl].key)>0){	//if key is larger than curr[lvl].key
				prev[lvl]=curr[lvl];
				curr[lvl]=curr[lvl].next[lvl];
			}
			if(curr[lvl]!=null && key.compareTo((T)curr[lvl].key)==0)	//if node with same key is present then do not include it
				return;
			if(lvl>0)
				if(prev[lvl]==null){
					curr[lvl-1]=root[lvl-1];
					prev[lvl-1]=null;
				}else{
					curr[lvl-1]=prev[lvl].next[lvl-1];
					prev[lvl-1]=prev[lvl];
				}
		}
		lvl=chooseLevel();	//choose random level for newNode to be at

		newNode = new SkipListNode(key,lvl+1);
		for(i=0;i<=lvl;i++){
			newNode.next[i]=curr[i];	//loop through to the appropriate level cap for newNode and make next array of newNode point to current larger than node array
			if(prev[i]==null){	//if prev node null then newNode is the new first node in the skipList for that level
				root[i]=newNode;
			}else
				prev[i].next[i]=newNode;
		}


	}

	public boolean delete(T key)
	{
		//Your code goes here
		if(isEmpty()){
			return false;
		}else{
			if(search(key)==null){
				return false;
			}
			SkipListNode curr;
			SkipListNode prev;
			int lvl;
			for(lvl=maxLevel-1;lvl>=0 && root[lvl]==null;lvl--);	//find highest non-null lvl
			prev=curr = root[lvl];
			for(int i=0;i>=0;i--){
				while(key.compareTo((T)curr.next[i].key)>0){
					prev=curr;
					curr=curr.next[i];
				}
				prev.next[i]=curr.next[i];
			}
			return true;
		}
	}

	public T first()
	{
		//Your code goes here
		if(isEmpty())
			return null;
		else
			return root[0].key;
	}

	public T last()
	{
		//Your code goes here
		int i=0;//keep count of next array
		SkipListNode curr = root[0];
		if (isEmpty())
			return null;
		else{
			while(curr.next[0]!=null){
				curr = curr.next[0];
				i++;
			}
			return (T)curr.key;
		}
	}



	public T search(T key)
	{
		//Your code goes here
		int lvl;
		SkipListNode curr,prev;
		for(lvl=maxLevel-1;lvl>=0 && root[lvl]==null;lvl--);	//loop till find max non-null level
		prev=curr=root[lvl];
		while(true){
			if(key.compareTo((T)curr.key)==0)
				return (T)curr.key;
			else if(key.compareTo((T)curr.key)<0){
				if(lvl==0)
					return null;
				else if(curr==root[lvl])
					curr=root[--lvl];
				else
					curr=prev.next[--lvl];
			}
			else{
				prev=curr;
				if(curr.next[lvl]!=null)
					curr=curr.next[lvl];
				else{
					for(lvl--;lvl>=0 && curr.next[lvl]==null;lvl--);
					if(lvl>=0)
						curr = curr.next[lvl];
					else
						return null;
				}
			}
		}

	}
}