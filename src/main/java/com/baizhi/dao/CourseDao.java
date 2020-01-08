package com.baizhi.dao;

import com.baizhi.entity.Course;

import java.util.List;

public interface CourseDao {

    public List<Course> selectAll(String id);

    public void insert(Course course);

    public void delete(String id);

    public Course selectById(String id);

}
