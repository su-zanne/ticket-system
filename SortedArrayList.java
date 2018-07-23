import java.util.*;
/**
 * This class contains a method which will add new elements to the ArrayList in
 * a sorted order.
 *
 * @author Susanne Christensen
 * @version 05/12/2017
 */
public class SortedArrayList<E extends Comparable<E>> extends ArrayList<E>
{
    /**
     * Create a new SortedArrayList object.   
     */
    public SortedArrayList()
    {
        super();
    }
    
    /**
     * This method adds a new element to the ArrayList in a sorted order.
     * 
     * @param   E    The element to add to the list.   
     */
    @Override
    public boolean add(E e)
    {
        for (int i = 0; i < super.size(); i++) {
            if (super.get(i).compareTo(e) > 0) {
                super.add(i, e);
                return true;
            }
        }
        return super.add(e); 
    }
}
