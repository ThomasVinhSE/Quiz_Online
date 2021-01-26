/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vinhnq.utilities;

/**
 *
 * @author Vinh
 */
public class PagingModel {
    public static int getNumberOfPage(int size,int limit)
    {
        int number = 1;
        int i;
        for(i = limit ; i < size ; i += limit)
        {
            number ++;
        }
        if(size > i)
            number++;
        return number;
    }
}
