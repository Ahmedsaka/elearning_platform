-- Create course table
IF  NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[course]') AND type in (N'U'))
    BEGIN
        CREATE TABLE [dbo].[course]
        (
            [id]                        BIGINT IDENTITY (1,1) PRIMARY KEY NOT NULL,
            [course_name]               VARCHAR(200)                      NOT NULL ,
            [instructor_name]           VARCHAR(200)                      NOT NULL ,
            [course_content_location]   VARCHAR(200)                      NOT NULL ,
            [create_date]               DATETIME
        )
    END
GO


-- Create course sp
IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = object_id (N'[dbo].[sp_create_course]') AND OBJECTPROPERTY(id, N'IsProcedure') = 1)
    DROP PROCEDURE sp_create_course
GO
CREATE PROCEDURE sp_create_course( @id BIGINT=NULL OUTPUT,
                                 @course_name VARCHAR(200),
                                 @instructor_name VARCHAR(200),
                                 @course_content_location VARCHAR(200))
AS
    SET NOCOUNT ON
        INSERT INTO [dbo].[course]( course_name, instructor_name, course_content_location, create_date)
        VALUES( @course_name, @instructor_name, @course_content_location, GETDATE())
    SELECT @id = SCOPE_IDENTITY()
        RETURN @@Error
GO


-- Create course_student table
IF  NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[course_student]') AND type in (N'U'))
    BEGIN
        CREATE TABLE [dbo].[course_student]
        (
            [id]              BIGINT IDENTITY (1,1) PRIMARY KEY NOT NULL,
            [course_id]       BIGINT                FOREIGN KEY REFERENCES dbo.course(id),
            [student_id]      BIGINT                FOREIGN KEY REFERENCES dbo.admin_user(id),
        )
    END
GO


-- sp find course by name
IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = object_id (N'[dbo].[sp_find_course_by_name]') AND OBJECTPROPERTY(id, N'IsProcedure') = 1)
    DROP PROCEDURE sp_find_course_by_name
GO
CREATE PROCEDURE sp_find_course_by_name(@course_name VARCHAR(200))
    AS SET NOCOUNT ON
        BEGIN
            SELECT * FROM dbo.course
            WHERE dbo.course.course_name LIKE '%' + @course_name + '%'
        END
    RETURN @@Error
GO


-- sp find all courses
IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = object_id (N'[dbo].[sp_find_all_courses]') AND OBJECTPROPERTY(id, N'IsProcedure') = 1)
    DROP PROCEDURE sp_find_all_courses
GO
CREATE PROCEDURE sp_find_all_courses
    AS SET NOCOUNT ON
        BEGIN
            SELECT * FROM dbo.course
        END
    RETURN @@Error
GO

-- sp find course by instructor name
IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = object_id (N'[dbo].[sp_find_course_by_instructor_name]') AND OBJECTPROPERTY(id, N'IsProcedure') = 1)
    DROP PROCEDURE sp_find_course_by_instructor_name
GO
CREATE PROCEDURE sp_find_course_by_instructor_name(@instructor_name VARCHAR(200))
    AS SET NOCOUNT ON
        BEGIN
            SELECT * FROM dbo.course
            WHERE dbo.course.instructor_name LIKE '%' + @instructor_name + '%'
        END
    RETURN @@Error
GO



-- Create course_student
IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = object_id (N'[dbo].[sp_create_course_student]') AND OBJECTPROPERTY(id, N'IsProcedure') = 1)
    DROP PROCEDURE sp_create_course_student
GO
CREATE PROCEDURE sp_create_course_student( @id BIGINT=NULL OUTPUT,
                                           @course_id BIGINT,
                                           @student_id BIGINT)
AS
    SET NOCOUNT ON
        INSERT INTO [dbo].[course_student]( course_id, student_id)
        VALUES( @course_id, @student_id)

SELECT @id = SCOPE_IDENTITY()
    RETURN @@Error
GO