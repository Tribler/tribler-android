package org.tribler.tsap.thumbgrid;

import java.io.File;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.v4.util.LruCache;
import android.widget.ImageView;

public class ThumbCache {

	private int mMemSize, mDiskSize;
	private String mUniqueFolderName;
	
	private final Object mDiskCacheLock = new Object();
	
	private LruCache<String, Bitmap> mMemCache;
	//private DiskLruCache mDiskCache;
	
	public ThumbCache(int memSize, int diskSize, String uniqueFolderName)
	{
		this.mMemSize = memSize;
		this.mDiskSize = diskSize;
		this.mUniqueFolderName = uniqueFolderName;
		
	    mMemCache = new LruCache<String, Bitmap>(this.mMemSize) {
	        @Override
	        protected int sizeOf(String key, Bitmap bitmap) {
	            // The cache size will be measured in kilobytes rather than
	            // number of items.
	            return bitmap.getByteCount() / 1024;
	        }
	    };
	    
	    //mDiskCache = new DiskLruCache(null, dskCacheSize, dskCacheSize, dskCacheSize);
	}

	public void addThumb(String key, Bitmap bitmap)
	{
	    if (getThumbFromMem(key) == null) {
	        mMemCache.put(key, bitmap);
	    }
	    
//	    // Also add to disk cache
//	    synchronized (mDiskCacheLock) {
//	        if (mDiskCache != null && mDiskCache.get(key) == null) {
//	            mDiskCache.put(key, bitmap);
//	        }
//	    }

	}
	
	public Bitmap getThumb(String key)
	{
		Bitmap bitmap = getThumbFromMem(key);
		
		if(bitmap != null)
			return bitmap;
		
		return getThumbFromDisk(key);
	}
	
	private Bitmap getThumbFromMem(String key)
	{
	    return (Bitmap) mMemCache.get(key);		
	}
	
	private Bitmap getThumbFromDisk(String key)
	{
		return (Bitmap) null;
	}
	
	// Creates a unique subdirectory of the designated app cache directory. Tries to use external
	// but if not mounted, falls back on internal storage.
	public static File getDiskCacheDir(Context context, String uniqueName) {
	    // Check if media is mounted or storage is built-in, if so, try and use external cache dir
	    // otherwise use internal cache dir
	    final String cachePath =
	            Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) ||
	                    !Environment.isExternalStorageRemovable() ? context.getExternalCacheDir().getPath() :
	                            context.getCacheDir().getPath();

	    return new File(cachePath + File.separator + uniqueName);
	}

}
