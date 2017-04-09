package pw.ewen.WLPT.domain.DTO;

/**
 * Created by wen on 17-4-9.
 * DTO和domain object 之间的转化接口
 */
public interface DTOConvert<DTO,D> {
    /**
     * DTO To D 转化
     */
    D doForward(DTO dto);

    /**
     * D To DTO 转化
     */
    DTO doBackward(D d);
}
