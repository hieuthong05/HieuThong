
package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import utils.DbUtils;

public class ExamCategoryDAO {
    private static final String GET_ALL_EXAM_CATEGORIES= "SELECT category_id,category_name,description FROM tblExamCategories ";
    
    
    public List<ExamCategoryDTO> getAll(){
        List<ExamCategoryDTO> exam_categories = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try{
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(GET_ALL_EXAM_CATEGORIES);
            rs=ps.executeQuery();
            while(rs.next()){
                ExamCategoryDTO examcategory = new ExamCategoryDTO();
                examcategory.setCategory_id(rs.getInt("category_id"));
                examcategory.setCategory_name(rs.getString("category_name"));
                examcategory.setDescription(rs.getString("description"));
                
                exam_categories.add(examcategory);
            }
            
        } catch (Exception e) {
            System.err.println("Error  in getAll():"+e.getMessage());
            e.printStackTrace();
        }finally{
            closeResource(conn,ps,rs);
        }
        return exam_categories;
    }

    private void closeResource(Connection conn, PreparedStatement ps, ResultSet rs) {
        try{
            if(rs!=null){
                rs.close();
            }
            if(ps !=null){
                ps.close();
            }
            if(conn !=null){
                conn.close();
            }
        }catch (Exception e){
            System.err.println("Error closing resources: "+e.getMessage());
            e.printStackTrace();
        }
    }
    
    public static List<ExamCategoryDTO> getCategoryList() {
        List<ExamCategoryDTO> list = new ArrayList<>();

        ExamCategoryDTO quiz = new ExamCategoryDTO(1, "Quiz", "Short quizzes");
        ExamCategoryDTO midterm = new ExamCategoryDTO(2, "Midterm", "Midterm exam");
        ExamCategoryDTO finalExam = new ExamCategoryDTO(3, "Final", "Final exam");

        list.add(quiz);
        list.add(midterm);
        list.add(finalExam);

        return list;
    }
  

    
}
