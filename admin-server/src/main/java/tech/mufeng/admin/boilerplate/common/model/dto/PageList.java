package tech.mufeng.admin.boilerplate.common.model.dto;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;

import java.util.List;

@Data
public class PageList<T> {
    private Long count;

    private List<T> rows;

    public static <T> PageList<T> transformFromPage(IPage<T> rawPage) {
        if (rawPage == null) {
            return null;
        }
        PageList<T> instance = new PageList<>();
        instance.count = rawPage.getTotal();
        instance.rows = rawPage.getRecords();
        return instance;
    }
}
