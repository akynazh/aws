package cn.edu.xidian.aws.service;

import cn.edu.xidian.aws.exception.AwsNotFoundException;
import cn.edu.xidian.aws.pojo.po.Todo;
import cn.edu.xidian.aws.pojo.vo.record.RecordAddVO;
import cn.edu.xidian.aws.pojo.vo.todo.TodoListVO;
import cn.edu.xidian.aws.pojo.vo.todo.TodoVO;
import cn.edu.xidian.aws.pojo.vo.todo.TodosGetVO;
import cn.edu.xidian.aws.repository.TodoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author akynazh@gmail.com
 * @date 2025/4/2
 * @description
 */
@Service
public class TodoService {
    @Autowired
    private TodoRepository todoRepository;

    public void addTodo(RecordAddVO recordAddVO) {
        Todo todo = new Todo();
        BeanUtils.copyProperties(recordAddVO, todo);
        todo = todoRepository.save(todo);
        TodoVO todoVO = new TodoVO();
        BeanUtils.copyProperties(todo, todoVO);
    }

    public void delTodo(Long id) {
        todoRepository.deleteById(id);
    }

    public TodoListVO getTodos(TodosGetVO getVO) {
        PageRequest pr = PageRequest.of(getVO.getPage(), getVO.getSize());
        List<Todo> todoList = todoRepository.findAll(pr).getContent();
        long count = todoRepository.count();

        List<TodoVO> todoVOList = todoList.stream().map(Todo::toTodoVO).collect(Collectors.toList());
        TodoListVO result = new TodoListVO();
        result.setTodoVOList(todoVOList);
        result.setCount(count);
        return result;
    }
}
