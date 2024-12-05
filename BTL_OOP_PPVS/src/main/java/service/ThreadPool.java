package service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author Littl
 */
public class ThreadPool {
    private static final int RENDER_MAX_THREADS = 2; // vẽ ảnh trên app
    private static final int GETTER_MAX_THREADS = 1; // luồng lấy ảnh từ URL
    private static final int SETTER_MAX_THREADS = 1; // Setup cơ bản cho tài liệu
    
    private static ExecutorService bookRender = Executors.newFixedThreadPool(RENDER_MAX_THREADS);
    private static ExecutorService getter = Executors.newFixedThreadPool(GETTER_MAX_THREADS);
    private static ExecutorService setter = Executors.newFixedThreadPool(SETTER_MAX_THREADS);
    
    public static void renewPool() {
        // hủy tasks cũ
        bookRender.shutdownNow();
        getter.shutdownNow();
        
        // tạo pool nạp tasks mới
        bookRender = Executors.newFixedThreadPool(RENDER_MAX_THREADS);
        getter = Executors.newFixedThreadPool(GETTER_MAX_THREADS);
        setter = Executors.newFixedThreadPool(SETTER_MAX_THREADS);
    }
    
    public static void addRenderTask(Runnable task) {
        bookRender.execute(task);
    }
    
    public static void addSetterTask(Runnable task) {
        setter.execute(task);
    }
    
    public static void addGetterTask(Runnable task) {
        getter.execute(task);
    }
}
