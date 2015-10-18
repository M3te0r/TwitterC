package model;

import twitter.client.TweetModel;

import javax.swing.*;
import java.util.*;

/**
 * Created by Mathieu on 18/10/2015.
 */
public class TweetListModel extends DefaultListModel<TweetModel> {

    private long maxId;
    private long minId;

    public TweetListModel() {
        super();
        maxId = 0;
        minId = 0;
    }

    /**
     * Inserts the specified element as a component in this list at the
     * specified <code>index</code>.
     * <p>
     * Throws an <code>ArrayIndexOutOfBoundsException</code> if the index
     * is invalid.
     * <blockquote>
     * <b>Note:</b> Although this method is not deprecated, the preferred
     * method to use is <code>add(int,Object)</code>, which implements the
     * <code>List</code> interface defined in the 1.2 Collections framework.
     * </blockquote>
     *
     * @param element the component to insert
     * @param index   where to insert the new component
     * @throws ArrayIndexOutOfBoundsException if the index was invalid
     * @see #add(int, Object)
     * @see Vector#insertElementAt(Object, int)
     */
    @Override
    public void insertElementAt(TweetModel element, int index) {
        super.insertElementAt(element, index);
        checkMaxId(element.getInternalId());
    }

    public void sortModel(){
        List<TweetModel> list = Collections.list(elements());
        Collections.sort(list);
        removeAllElements();
        list.forEach(this::addElement);
    }

    /**
     * Adds the specified component to the end of this list.
     *
     * @param element the component to be added
     * @see Vector#addElement(Object)
     */
    @Override
    public void addElement(TweetModel element) {
        super.addElement(element);
        checkMaxId(element.getInternalId());
    }

    private void checkMaxId(long id){
        if(id > maxId) maxId = id;
    }

    public long getMaxId(){
        return maxId;
    }
}
