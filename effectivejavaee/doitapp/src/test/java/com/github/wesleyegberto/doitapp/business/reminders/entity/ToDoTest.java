package com.github.wesleyegberto.doitapp.business.reminders.entity;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author wesley
 */
public class ToDoTest {
    @Test
    public void testInvalidHighPriorityTodoWithoutDescription() {
        ToDo todo = new ToDo("High priority without description", null, 20);
        assertFalse(todo.isValid());
    }
    
    @Test
    public void testValidHighPriorityTodoWithDescription() {
        ToDo todo = new ToDo("High priority", "Some description", 20);
        assertTrue(todo.isValid());
    }
    
    @Test
    public void testValidLowPriorityTodoWithoutDescription() {
        ToDo todo = new ToDo("Low priority", null, 9);
        assertTrue(todo.isValid());
    }
}
