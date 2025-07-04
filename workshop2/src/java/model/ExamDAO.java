
package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import utils.DbUtils;

public class ExamDAO {
    private static final String GET_EXAM_BY_ID = "  SELECT exam_id, exam_title, subject, total_marks, duration FROM tblExams WHERE category_id = ?";
    private static final String CREATE_EXAM = "INSERT INTO tblExams (exam_title, subject, category_id, total_marks, duration) VALUES (?, ?, ?, ?, ?)";
    public List<ExamDTO> getExamsByCategory(int categoryId) {
        
        List<ExamDTO> list = new ArrayList<>();
        ExamDTO exam = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try{
            conn = DbUtils.getConnection();
            ps= conn.prepareStatement(GET_EXAM_BY_ID);
            ps.setInt(1,categoryId);
            rs = ps.executeQuery();
            
            while(rs.next()){
                
                exam = new ExamDTO();
                exam.setExam_id(rs.getInt("exam_id"));
                exam.setExam_title(rs.getString("exam_title"));
                exam.setSubject(rs.getString("subject"));
                exam.setTotal_marks(rs.getInt("total_marks"));
                exam.setDuration(rs.getInt("duration"));
                
                list.add(exam);
            }
            
        }catch(Exception e){
            System.err.println("Error in getExamsByName():"+e.getMessage());
        }finally{
            closeResource(conn, ps, rs);
        }
        return list;
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
    
    public boolean createExam(ExamDTO exam) {
        boolean success = false;
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(CREATE_EXAM);

            ps.setString(1, exam.getExam_title());
            ps.setString(2, exam.getSubject());
            ps.setInt(3, exam.getCategory_id());
            ps.setInt(4, exam.getTotal_marks());
            ps.setInt(5, exam.getDuration());

            int rows = ps.executeUpdate();
            success = rows > 0;

        } catch (Exception e) {
            System.err.println("Error in insertExam(): " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResource(conn, ps, null);
        }

        return success;
    }

}
