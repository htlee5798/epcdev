package lcn.module.common.file;

import java.util.Collection;
import java.util.Set;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.web.multipart.MultipartFile;

public class FileUploadUtils
{
  public static String validate(MultipartFile multipartFile, Long maximumSize, Set<String> allowedTypesSet, Set<String> allowedExtensionsSet, MessageSourceAccessor messageSourceAccessor)
  {
    String msg = null;

    if (multipartFile == null) {
      msg = messageSourceAccessor.getMessage("error.uploading", new Object[] { "file is null" }, "Error uploading");
    } else {
      String filename = multipartFile.getOriginalFilename();
      String contentType = multipartFile.getContentType();
      long fileSize = multipartFile.getSize();

      if (fileSize < 1L) {
        msg = messageSourceAccessor.getMessage("error.uploading", new Object[] { "file is empty" }, "Error uploading");
      }
      else if ((maximumSize != null) && (maximumSize.longValue() < fileSize)) {
        msg = messageSourceAccessor.getMessage("error.file.too.large", new Object[] { filename, "" + fileSize }, "File too large");
      }
      else if ((!(allowedTypesSet.isEmpty())) && (!(containsItem(allowedTypesSet, contentType)))) {
        msg = messageSourceAccessor.getMessage("error.file.type.not.allowed", new Object[] { filename, contentType }, "Content-Type not allowed");
      }
      else if ((!(allowedExtensionsSet.isEmpty())) && (!(hasAllowedExtension(allowedExtensionsSet, filename)))) {
        msg = messageSourceAccessor.getMessage("error.file.extension.not.allowed", new Object[] { filename, contentType }, "File extension not allowed");
      }
    }

    return msg;
  }

  private static boolean containsItem(Collection<String> itemCollection, String item)
  {
    return itemCollection.contains(item.toLowerCase());
  }

  private static boolean hasAllowedExtension(Collection<String> extensionCollection, String filename)
  {
    if (filename == null) {
      return false;
    }

    String lowercaseFilename = filename.toLowerCase();
    for (String extension : extensionCollection) {
      if (lowercaseFilename.endsWith(extension)) {
        return true;
      }
    }

    return false;
  }
}