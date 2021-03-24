package pw.ewen.WLPT.domains.dtoconvertors;

/**
 * Created by wen on 17-4-9.
 * DTO和domain object 之间的转化接口
 */
public interface DTOConvert<DTO,D> {
    /**
     * DTOs To D 转化
     */
    D doForward(DTO dto);

    /**
     * D To DTOs 转化
     */
     DTO doBackward(D d);
}
